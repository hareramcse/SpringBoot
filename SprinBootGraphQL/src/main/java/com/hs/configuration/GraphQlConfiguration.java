package com.hs.configuration;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.hs.entity.Person;
import com.hs.service.PersonService;

import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@Configuration
public class GraphQlConfiguration {

	@Value("classpath:graphql/schema.graphqls")
	private Resource schemaResource;

	@Autowired
	private PersonService personService;

	@Bean
	public GraphQL loadSchema() throws IOException {
		File schemaFile = schemaResource.getFile();
		SchemaParser schemaParser = new SchemaParser();
		TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schemaFile);
		RuntimeWiring runtimeWiring = buildRuntimeWiring();
		SchemaGenerator schemaGenerator = new SchemaGenerator();
		GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
		return GraphQL.newGraphQL(graphQLSchema).build();
	}

	private RuntimeWiring buildRuntimeWiring() {
		DataFetcher<List<Person>> fetcher1 = data -> {
			return (List<Person>) personService.findAll();
		};

		DataFetcher<Person> fetcher2 = data -> {
			return personService.findByEmail(data.getArgument("email"));
		};

		return RuntimeWiring.newRuntimeWiring().type("Query",
				typeWriting -> typeWriting.dataFetcher("getAllPerson", fetcher1).dataFetcher("findPerson", fetcher2))
				.build();
	}

}
