package eu.qcloud.security.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.qcloud.security.model.Authority;
import eu.qcloud.security.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    
    User findOneByApiKey(UUID apiKey);
    
    User findOneByApiKeyAndNodeId(UUID apiKey, Long nodeId);
    
    UserWithUuid findWithUuidById(Long id);
    
    User findUserById(Long id);
    
    List<UserWithUuid> findAllUsersByNodeId(Long nodeId);
    
    List<UserWithUuid> findAllByNodeIdAndEnabledTrue(Long nodeId);  
    
    interface UserWithUuid {
    	String getUsername();
    	String getFirstname();
    	String getLastname();    	
    	String getEmail();
    	List<Authority> getAuthorities();
    	UUID getApiKey();
    }    
    
}
