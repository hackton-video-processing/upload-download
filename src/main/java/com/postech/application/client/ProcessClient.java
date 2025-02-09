package com.postech.application.client;

import com.postech.domain.entities.Process;

import java.util.ArrayList;
import java.util.Optional;

public interface ProcessClient {

    Process createProcess(ArrayList<String> fileNames);

    Optional<Process> consultProcess(String processId);
}
