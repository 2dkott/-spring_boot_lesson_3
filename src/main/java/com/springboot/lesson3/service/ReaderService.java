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
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ReaderService {

    final private ReaderRepository readerRepository;
    final private IssueRepository issueRepository;

    public Reader add(ReaderRequest request) {
        Reader reader = new Reader(request.getReaderName());
        return readerRepository.save(reader);
    }
    public List<Reader> getAll() {
        return StreamSupport.stream(readerRepository.findAll().spliterator(), false).toList();
    }

    public Optional<Reader> getByID(long id) {
        return readerRepository.findById(id);
    }

    public List<Issue> getAllIssues(Reader reader) {
        return issueRepository.findIssuesByReader(reader);
    }

    public Reader removeById(long id) throws NoReaderException {
        Reader reader = readerRepository.findById(id).orElseThrow(() -> new NoReaderException(id));;
        readerRepository.delete(reader);
        return reader;
    }
}
