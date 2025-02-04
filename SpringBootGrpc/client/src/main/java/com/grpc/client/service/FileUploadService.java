package com.grpc.client.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.protobuf.ByteString;
import com.hs.File;
import com.hs.FileMetadata;
import com.hs.FileUploadRequest;
import com.hs.FileUploadResponse;
import com.hs.FileUploadServiceGrpc;
import com.hs.UploadStatus;
import com.shared.proto.Constants;

import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Slf4j
@Service
public class FileUploadService {
	private FileUploadServiceGrpc.FileUploadServiceStub client;

	public FileUploadService(@GrpcClient(value = "grpc-service") FileUploadServiceGrpc.FileUploadServiceStub client) {
		this.client = client;
	}

	public String uploadFile(final MultipartFile multipartFile) {
		String fileName;
		int fileSize;
		InputStream inputStream;
		fileName = multipartFile.getOriginalFilename();

		try {
			fileSize = multipartFile.getBytes().length;
			inputStream = multipartFile.getInputStream();
		} catch (IOException e) {
			return "unable to extract file info";
		}

		StringBuilder response = new StringBuilder();
		CountDownLatch countDownLatch = new CountDownLatch(1);

		Metadata metadata = new Metadata();
		metadata.put(Constants.fileMetadataKey, FileMetadata.newBuilder().setFileNameWithType(fileName)
				.setContentLength(fileSize).build().toByteArray());

		StreamObserver<FileUploadRequest> fileUploadRequestStreamObserver = this.client
				.withInterceptors(MetadataUtils.newAttachHeadersInterceptor(metadata))
				.uploadFile(new StreamObserver<>() {
					@Override
					public void onNext(FileUploadResponse fileUploadResponse) {
						response.append(fileUploadResponse.getUploadStatus());
					}

					@Override
					public void onError(Throwable throwable) {
						response.append(UploadStatus.FAILED);
						throwable.printStackTrace();
						countDownLatch.countDown();
					}

					@Override
					public void onCompleted() {
						countDownLatch.countDown();
					}
				});

		byte[] fiveKB = new byte[5120];

		int length;

		try {
			while ((length = inputStream.read(fiveKB)) > 0) {
				log.info(String.format("sending %d length of data", length));
				var request = FileUploadRequest.newBuilder()
						.setFile(File.newBuilder().setContent(ByteString.copyFrom(fiveKB, 0, length))).build();
				fileUploadRequestStreamObserver.onNext(request);
			}
			inputStream.close();
			fileUploadRequestStreamObserver.onCompleted();
			countDownLatch.await();
		} catch (Exception e) {
			e.printStackTrace();
			response.append(UploadStatus.FAILED);
		}
		return response.toString();
	}

}
