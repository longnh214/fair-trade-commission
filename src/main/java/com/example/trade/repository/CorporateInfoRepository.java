package com.example.trade.repository;

import com.example.trade.entity.CorporateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateInfoRepository extends JpaRepository<CorporateInfo, Long> {
}