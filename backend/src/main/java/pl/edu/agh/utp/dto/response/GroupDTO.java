package pl.edu.agh.utp.dto.response;

import pl.edu.agh.utp.model.nodes.Group;

public record GroupDTO(Long groupId, String name) {

    public static GroupDTO fromGroup(Group group) {
        return new GroupDTO(group.getId(), group.getName());
    }

}
