package com.tech.hystrix;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import com.tech.entity.Student;
import com.tech.service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TestCollapseCommand extends HystrixCollapser<List<String>, String, Student> {

    private StudentService studentService;
    private Student student;

    public TestCollapseCommand(StudentService studentService, Student student) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("testCollapseCommand")).andCollapserPropertiesDefaults(
                HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(100)));
        this.studentService = studentService;
        this.student = student;
    }

    @Override
    public Student getRequestArgument() {
        return student;
    }

    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Student>> collapsedRequests) {
        List<Student> studentList = new ArrayList<>(collapsedRequests.size());
        studentList.addAll(collapsedRequests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));
        return new TestCommand(studentService, studentList);
    }

    @Override
    protected void mapResponseToRequests(List<String> batchResponse, Collection<CollapsedRequest<String, Student>> collapsedRequests) {
        int count = 0;
        for (CollapsedRequest<String, Student> collapsedRequest : collapsedRequests) {
            String user = batchResponse.get(count++);
            collapsedRequest.setResponse(user);
        }
    }

}
