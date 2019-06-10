package com.lms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lms.model.Person;
import com.lms.model.Role;
import com.lms.repository.MemberRepository;

@Service("memberService")
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberRepository memberRepository;

	@Override
	@Transactional
	public Role findPersonRole(String email) {
		return memberRepository.findPersonRole(email);

	}

	@Override
	public List<Person> findAll() {
		return memberRepository.findAll();
	}
	@Override
	@Transactional
	public Person findByEmail(String email) {
		
		return memberRepository.findByEmail(email);
		
	}

	@Override
	@Transactional
	public Optional<Person> findByID(Long id) {
		return memberRepository.findById(id);
	}

	@Override
	@Transactional
	public Person save(Person person) {
		return memberRepository.save(person);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		 memberRepository.deleteById(id);
		 
	}
	@Override
	@Transactional
	public boolean isPersonExist(Long id) {
		
		 return memberRepository.existsById(id);
	}

}
