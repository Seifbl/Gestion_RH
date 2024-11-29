package com.fl2.gd;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fl2.gd.Entities.AppUser;
import com.fl2.gd.Entities.Role;
import com.fl2.gd.Repositories.RoleRepository;
import com.fl2.gd.Repositories.UserRepository;





@SpringBootApplication
@EnableScheduling
public class GestionDemandesApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionDemandesApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            Role adminRole = roleRepo.findByAuthority("ADMIN").orElse(null);
            Role chefDepartementRole = roleRepo.findByAuthority("CHEF DEPARTEMENT").orElse(null);
            Role userRole = roleRepo.findByAuthority("USER").orElse(null);

            if (adminRole == null) {
                adminRole = new Role("ADMIN");
                adminRole = roleRepo.save(adminRole);
            }

            if (chefDepartementRole == null) {
                chefDepartementRole = new Role("CHEF DEPARTEMENT");
                chefDepartementRole = roleRepo.save(chefDepartementRole);
            }

            if (userRole == null) {
                userRole = new Role("USER");
                userRole = roleRepo.save(userRole);
            }

            AppUser adminUser = userRepo.findByUsername("admin").orElse(null);
            AppUser chefDepartementUser = userRepo.findByUsername("chef departement").orElse(null);

            if (adminUser == null) {
                Set < Role > roles = new HashSet < > ();
                roles.add(adminRole);

                AppUser admin = new AppUser();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("adminPassword"));
                admin.setAuthorities(roles);
                userRepo.save(admin);
            }

            if (chefDepartementUser == null) {
                Set < Role > roles = new HashSet < > ();
                roles.add(chefDepartementRole);

                AppUser chefDepartement = new AppUser();
                chefDepartement.setUsername("chef departement");
                chefDepartement.setPassword(passwordEncoder.encode("chefDepartementPassword"));
                chefDepartement.setAuthorities(roles);
                userRepo.save(chefDepartement);
            }

        };
    }


}
