package com.apiimplementation.service;

import com.apiimplementation.dto.RegisterRequestDto;
import com.apiimplementation.enums.Role;
import com.apiimplementation.model.Employer;
import com.apiimplementation.model.JobSeeker;
import com.apiimplementation.model.User;
import com.apiimplementation.repository.EmployerRepository;
import com.apiimplementation.repository.JobSeekerRepository;
import com.apiimplementation.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final EmployerRepository employerRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final PasswordEncoder passwordEncoder;

    // Register Employer or Seeker
    public User register(RegisterRequestDto dto) {

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(
                passwordEncoder.encode(dto.getPassword())
        );
        user.setRole(dto.getRole());

        User savedUser = userRepository.save(user);

        // EMPLOYER registration
        if (dto.getRole() == Role.EMPLOYER) {

            Employer employer = new Employer();
            employer.setCompanyName(dto.getCompanyName());
            employer.setUser(savedUser);

            employerRepository.save(employer);
        }

        // SEEKER registration
        if (dto.getRole() == Role.SEEKER) {

            JobSeeker jobSeeker = new JobSeeker();
            jobSeeker.setName(dto.getName());
            jobSeeker.setResumeSummary(dto.getResumeSummary());
            jobSeeker.setUser(savedUser);

            jobSeekerRepository.save(jobSeeker);
        }

        return savedUser;
    }

    // Spring Security
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User Not Found"));

        return org.springframework.security.core.userdetails
                .User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}