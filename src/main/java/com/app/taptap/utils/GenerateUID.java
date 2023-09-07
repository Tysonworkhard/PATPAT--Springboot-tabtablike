package com.app.taptap.utils;

import java.util.UUID;

public class GenerateUID {
    public String generateUid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
