package pl.edu.agh.utp.records.simple;

import java.util.UUID;

public record SimpleTransaction(UUID transactionId, String description, String date) {}
