package com.pirate.arena.app.request;

public record RequestAmazon(String type, String methodArn, String authorizationToken) {
}
