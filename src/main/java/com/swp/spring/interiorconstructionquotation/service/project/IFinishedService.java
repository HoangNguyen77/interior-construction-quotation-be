package com.swp.spring.interiorconstructionquotation.service.project;

import org.springframework.http.ResponseEntity;

public interface IFinishedService {
    public ResponseEntity<?> createFinished(FinishedRequest finishedRequest);
    public ResponseEntity<?> updateFinished(FinishedRequest finishedRequest);

}
