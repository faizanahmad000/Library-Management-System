package com.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lms.model.Person;
import com.lms.model.Role;

@Repository("memberRepository")
public interface MemberRepository extends JpaRepository<Person, Long> {
	Person findByEmailAndPassword(String email,String password);
	Person findByEmail(String email);
	@Query("select p.role from Person p WHERE p.email=:email")
	Role findPersonRole(@Param("email") String email);
}
