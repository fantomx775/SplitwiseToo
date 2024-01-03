package pl.edu.agh.utp.service;

import org.junit.jupiter.api.Test;
import pl.edu.agh.utp.model.nodes.User;
import pl.edu.agh.utp.records.UserBalance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


class GroupServiceTest {


    @Test
    void calculateReimbursments() {
        List<User> users = Stream.of("ka", "luk", "mam", "mi","paj","sad","sz")
                .map(i->new User(i, i, i))
                .toList();
        List<UserBalance> userBalances =  Stream.of(
                new UserBalance(users.get(0), -12.98),
                new UserBalance(users.get(1), 105.98),
                new UserBalance(users.get(2), 128.85),
                new UserBalance(users.get(3), 62.10),
                new UserBalance(users.get(4), -358.98),
                new UserBalance(users.get(5), 1.38),
                new UserBalance(users.get(6), 73.65)
        ).toList();

        System.out.println(GroupService.calculateReimbursments(userBalances).stream().map(i->i.toString()).toList());







    }
}