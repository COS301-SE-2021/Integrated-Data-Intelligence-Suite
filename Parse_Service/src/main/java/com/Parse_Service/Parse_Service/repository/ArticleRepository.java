package com.Parse_Service.Parse_Service.repository;

import com.Parse_Service.Parse_Service.dataclass.ParsedArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<ParsedArticle, Long> {
}
