package com.smartdatasolutions.test.impl;

import com.smartdatasolutions.test.Member;
import com.smartdatasolutions.test.MemberExporter;
import com.smartdatasolutions.test.MemberFileConverter;
import com.smartdatasolutions.test.MemberImporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends MemberFileConverter {

    @Override
    protected MemberExporter getMemberExporter() {
        return new MemberExporterImpl();
    }

    @Override
    protected MemberImporter getMemberImporter() {
        return new MemberImporterImpl();
    }

    @Override
    protected List<Member> getNonDuplicateMembers(List<Member> membersFromFile) {
        // Remove duplicates based on member id
        Map<String, Member> memberMap = new HashMap<>();
        for (Member member : membersFromFile) {
            memberMap.put(member.getId(), member);
        }
        return new ArrayList<>(memberMap.values());
    }

    @Override
    protected Map<String, List<Member>> splitMembersByState(List<Member> validMembers) {
        Map<String, List<Member>> membersByState = new HashMap<>();
        for (Member member : validMembers) {
            String state = member.getState();
            membersByState.computeIfAbsent(state, k -> new ArrayList<>()).add(member);
        }
        return membersByState;
    }

    public static void main(String[] args) {
        Main main = new Main();
        try {
            main.convert(new File("Members.txt"), "outputDirectory", "outputFile.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
