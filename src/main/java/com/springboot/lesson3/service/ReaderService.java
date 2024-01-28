package com.springboot.lesson3.service;

import com.springboot.lesson3.api.ReaderRequest;
import com.springboot.lesson3.model.Issue;
import com.springboot.lesson3.model.Reader;
import com.springboot.lesson3.repository.IssueRepository;
import com.springboot.lesson3.repository.ReaderRepository;
import jakarta.security.auth.message.callback.PrivateKeyCallback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReaderService {

    final private ReaderRepository readerRepository;
    final private IssueRepository issueRepository;

    public Reader add(ReaderRequest request) {
        Reader reader = new Reader(request.getReaderName());
        return readerRepository.add(reader);
    }
    public List<Reader> getAll() {
        return readerRepository.getAll();
    }

    public Optional<Reader> getByID(long id) {
        return Optional.ofNullable(readerRepository.getReaderById(id));
    }

    public List<Issue> getAllIssues(Reader reader) {
        return issueRepository.getIssuesByReaderId(reader.getId());
    }

    public Optional<Reader> removeById(long id) {
        Optional<Reader> readerToDelete = Optional.ofNullable(readerRepository.getReaderById(id));
        readerToDelete.ifPresent(readerRepository::removeReader);
        return readerToDelete;
    }
}
