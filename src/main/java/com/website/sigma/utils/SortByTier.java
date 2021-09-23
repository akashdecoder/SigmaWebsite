package com.website.sigma.utils;

import com.website.sigma.model.Member;

import java.util.Comparator;

public class SortByTier implements Comparator<Member> {
    public int compare(Member a, Member b) {
        return (int) (a.getTier() - b.getTier());
    }
}