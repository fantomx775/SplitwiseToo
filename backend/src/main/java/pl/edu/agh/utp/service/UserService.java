package pl.edu.agh.utp.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.agh.utp.dto.request.LoginRequest;
import pl.edu.agh.utp.dto.response.GroupDTO;
import pl.edu.agh.utp.dto.response.UserDTO;
import pl.edu.agh.utp.exceptions.InvalidPasswordException;
import pl.edu.agh.utp.exceptions.UserNotFoundException;
import pl.edu.agh.utp.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public UserDTO authenticateUser(LoginRequest request)
      throws UserNotFoundException, InvalidPasswordException {
    return userRepository
        .findByEmail(request.email())
        .map(
            user -> {
              if (!user.getPassword().equals(request.password())) {
                throw new InvalidPasswordException("Invalid password");
              }
              return user;
            })
        .map(UserDTO::fromUser)
        .orElseThrow(
            () -> new UserNotFoundException("User not found with email: " + request.email()));
  }

  public List<GroupDTO> findGroupsByUserId(Long userId) {
    return userRepository.findGroupsByUserId(userId);
  }
}
