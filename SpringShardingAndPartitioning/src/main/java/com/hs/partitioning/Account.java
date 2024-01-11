package com.hs.partitioning;

import java.util.UUID;

public record Account(UUID id, String name, int countryCode, String description) {

}
