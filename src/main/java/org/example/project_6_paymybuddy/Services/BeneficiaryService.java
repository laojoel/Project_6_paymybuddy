package org.example.project_6_paymybuddy.Services;

import org.example.project_6_paymybuddy.Models.Beneficiary;
import org.example.project_6_paymybuddy.Models.User;
import org.example.project_6_paymybuddy.Proxies.BeneficiaryProxy;
import org.example.project_6_paymybuddy.Proxies.UserProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import static org.example.project_6_paymybuddy.Constant.*;
import static org.example.project_6_paymybuddy.ApplicationStarter.logger;

@Service
public class BeneficiaryService {

    @Autowired
    private BeneficiaryProxy beneficiaryProxy;
    @Autowired
    private UserProxy userProxy;

    public byte addBeneficiary(int holder, String relationEmail) {
        User relationUser = userProxy.findUserWithEmail(relationEmail);
        if (relationUser == null) {return ADD_BENEFICIARY_UNKNOWN_EMAIL;}
        else {
            if (beneficiaryProxy.findBeneficiaryByIds(holder,relationUser.getId())!=null) {return ADD_BENEFICIARY_DUPLICATED;}
            else {
                logger.info("user id " + holder + " added user id " + relationUser.getId() + " as beneficiary");
                beneficiaryProxy.addBeneficiary(holder,relationUser.getId()); return ADD_BENEFICIARY_SUCCESS;
            }
        }
    }

    public List<User> getUsersBeneficiariesForUserId(int holderId) {
        List<Beneficiary> beneficiaries = beneficiaryProxy.findAllBeneficiariesForUserId(holderId);

        List<Integer> userIds = new ArrayList<>();
        for (Beneficiary beneficiary : beneficiaries) {userIds.add(beneficiary.getRelation());}

        List<User> users = userProxy.findUsersFromIdArray(userIds);
        users.sort(Comparator.comparing(User::getUsername));
        return users;
    }

}
