package com.swp.spring.interiorconstructionquotation.service.finished;

import org.springframework.http.ResponseEntity;

public interface IFinishedProjectService {
    public ResponseEntity<?> updateIsConstruction(int headerId);
    public ResponseEntity<?> createFinishedProject(FinishedProjectRequest finishedProjectRequest);
    public ResponseEntity<?> updateFinishedProject(FinishedProjectRequest finishedProjectRequest);
}
