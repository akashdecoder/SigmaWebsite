package com.website.sigma.utils;

import com.website.sigma.model.Member;

import java.util.Comparator;

public class SortByTier implements Comparator<Member> {
    public int compare(Member a, Member b) {

        if(a.getTier() == b.getTier()) {
            return a.getFirstname().compareTo(b.getFirstname());
        }

        return (int) (a.getTier() - b.getTier());
    }
}