package pl.edu.agh.utp.service;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.agh.utp.dto.request.LoginRequest;
import pl.edu.agh.utp.dto.request.RegisterRequest;
import pl.edu.agh.utp.dto.response.GroupDTO;
import pl.edu.agh.utp.dto.response.UserDTO;
import pl.edu.agh.utp.exceptions.InvalidPasswordException;
import pl.edu.agh.utp.model.nodes.User;
import pl.edu.agh.utp.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public Optional<UserDTO> authenticateUser(LoginRequest request) throws InvalidPasswordException {
    return userRepository
        .findByEmail(request.email())
        .map(
            user -> {
              if (!user.getPassword().equals(request.password())) {
                throw new InvalidPasswordException("Invalid password");
              }
              return user;
            })
        .map(UserDTO::fromUser);
  }

  public List<GroupDTO> findGroupsByUserId(Long userId) {
    return userRepository.findGroupsByUserId(userId);
  }

  public Optional<UserDTO> createUser(RegisterRequest request) {
    if (userRepository.findByEmail(request.email()).isPresent()) {
      return Optional.empty();
    }
    return Optional.of(new User(request.name(), request.email(), request.password()))
        .map(userRepository::save)
        .map(UserDTO::fromUser);
  }
}
