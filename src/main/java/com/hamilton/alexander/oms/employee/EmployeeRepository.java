package com.hamilton.alexander.oms.employee;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {

    static final String FIELD_TAKEN_QUERY_WITH_BLANK_WHERE_CLAUSE = """
            SELECT
                COUNT(e) > 0
            FROM
                Employee e
            WHERE
            """;

    Page<EmployeePrj> findAllEmployeePrjBy(Pageable pageable);

    Optional<EmployeePrj> findEmployeePrjById(Long id);

    @Query(FIELD_TAKEN_QUERY_WITH_BLANK_WHERE_CLAUSE + "e.email = :email")
    Boolean checkEmailTaken(String email);

    @Query(FIELD_TAKEN_QUERY_WITH_BLANK_WHERE_CLAUSE + "e.phone = :phone")
    Boolean checkPhoneTaken(String phone);

    @Query(FIELD_TAKEN_QUERY_WITH_BLANK_WHERE_CLAUSE + "e.jwtSub = :jwtSub")
    Boolean checkJwtSubTaken(UUID jwtSub);

    @Query(FIELD_TAKEN_QUERY_WITH_BLANK_WHERE_CLAUSE + "e.email = :email AND e.id <> :id")
    Boolean checkEmailTakenByAnotherEmployee(String email, Long id);

    @Query(FIELD_TAKEN_QUERY_WITH_BLANK_WHERE_CLAUSE + "e.phone = :phone AND e.id <> :id")
    Boolean checkPhoneTakenByAnotherEmployee(String phone, Long id);

    @Query(FIELD_TAKEN_QUERY_WITH_BLANK_WHERE_CLAUSE + "e.jwtSub = :jwtSub AND e.id <> :id")
    Boolean checkJwtSubTakenByAnotherEmployee(UUID jwtSub, Long id);

}
