package pl.edu.agh.utp.dto.response;

import pl.edu.agh.utp.model.nodes.User;

public record UserDTO(Long id, String name, String email) {
  public static UserDTO fromUser(User user) {
    return new UserDTO(user.getId(), user.getName(), user.getEmail());
  }
}
