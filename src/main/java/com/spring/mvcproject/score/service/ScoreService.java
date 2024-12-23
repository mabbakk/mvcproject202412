package com.spring.mvcproject.score.service;

import com.spring.mvcproject.score.dto.request.ScoreCreateDto;
import com.spring.mvcproject.score.dto.response.ScoreDetailDto;
import com.spring.mvcproject.score.dto.response.ScoreListDto;
import com.spring.mvcproject.score.entity.Score;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

// 역할: 컨트롤러의 요청을 데이터베이스에 저장하기쉽게 변환
//       중간 핵심 계산, 데이터 변환처리를 수행, 트랜잭션 처리
@Service
public class ScoreService {

    private Map<Long, Score> scoreStore = new HashMap<>();

    private Long nextId = 1L;

    public ScoreService() {
        Score s1 = new Score(nextId++, "김말복", 100, 88, 75);
        Score s2 = new Score(nextId++, "박수포자", 55, 95, 15);
        Score s3 = new Score(nextId++, "김마이클", 4, 100, 40);
        Score s4 = new Score(nextId++, "세종대왕", 100, 0, 90);

        scoreStore.put(s1.getId(), s1);
        scoreStore.put(s2.getId(), s2);
        scoreStore.put(s3.getId(), s3);
        scoreStore.put(s4.getId(), s4);
    }

    // 목록조회 요청 핵심 로직 처리
    public List<ScoreListDto> getList(String sort) {
        // 저장된 성적 정보를 모두 읽어옴
        List<Score> scoreList = new ArrayList<>(scoreStore.values());

        // 원본 성적 리스트를 클라이언트가 원하는 모양으로 DTO변환
        List<ScoreListDto> responseData = scoreList
                .stream()
                .map(score -> new ScoreListDto(score))
                .collect(Collectors.toList());

        // 석차 처리
        calculateListRank(responseData);

        // 정렬 파라미터 처리
        responseData.sort(getScoreComparator(sort));

        // 컨트롤러에게 정제된 데이터 반환
        return responseData;
    }

    // 성적 단일 조회 핵심 비즈로직 처리
    public ScoreDetailDto getDetail(Long id) {
        // 데이터베이스(Map)에서 단일 조회 수행
        Score targetStudent = scoreStore.get(id);

        // 예외처리
        if (targetStudent == null) {
            throw new IllegalStateException(id + " 성적정보 가 존재하지 않습니다.");
        }

        // 석차와 총 학생수를 구하기 위해 학생 목록을 가져옴
        List<Score> scoreList = new ArrayList<>(scoreStore.values());

        scoreList.sort(Comparator.comparing((Score s) -> s.getKor() + s.getEng() + s.getMath()).reversed());

        int rank = 1;
        for (Score s : scoreList) { // 전체 학생을 순회하면서
            if (s.getId().equals(targetStudent.getId())) { // 현재 발견된 학생을 찾으면 스톱
                break;
            }
            rank++;
        }

        // Score엔터티를 ScoreDetailDto로 변환
        ScoreDetailDto responseDto = new ScoreDetailDto(targetStudent, scoreList.size());
        responseDto.setRank(rank);

        return responseDto;
    }


    // 성적정보 생성 비즈니스로직 처리
    public Score create(ScoreCreateDto dto) {
        // ScoreCreateDto를 Score로 변환하는 작업
        Score score = dto.toEntity();
        score.setId(nextId++);

        // 데이터베이스에 저장
        scoreStore.put(score.getId(), score);

        return score;
    }

    // 성적정보 삭제 비즈니스 로직 처리
    public void remove(Long id) {
        Score removed = scoreStore.remove(id);
        if (removed == null) {
            throw new IllegalStateException("해당 성적정보는 존재하지 않습니다. id = " + id);
        }
    }


    // 전체조회시 석차 구하기
    private static void calculateListRank(List<ScoreListDto> responseList) {
        // 석차 구하기
        // 총점 내림차로 정렬
        responseList.sort(comparing(ScoreListDto::getAverage).reversed());

        int currentRank = 1; // 현재 등수
        for (ScoreListDto dto : responseList) {
            dto.setRank(currentRank++);
        }
    }

    // 정렬 처리를 위한 정렬기 생성 유틸 메서드
    private Comparator<ScoreListDto> getScoreComparator(String sort) {
        Comparator<ScoreListDto> comparing = null;
        switch (sort) {
            case "id":
                comparing = comparing(ScoreListDto::getId);
                break;
            case "name":
                comparing = comparing(ScoreListDto::getMaskingName);
                break;
            case "average":
                comparing = comparing(ScoreListDto::getAverage).reversed();
        }
        return comparing;
    }
}
