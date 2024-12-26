package com.spring.mvcproject.board.service;

import com.spring.mvcproject.board.dto.request.BoardSaveDto;
import com.spring.mvcproject.board.dto.response.BoardDetailResponse;
import com.spring.mvcproject.board.dto.response.BoardListDto;
import com.spring.mvcproject.board.entity.Board;
import com.spring.mvcproject.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// 컨트롤러와 리포지토리 사이에서 중간 잡다한 처리(DTO 변환, 트랜잭션 처리 등) 담당
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 목록조회시 데이터 변환처리
    public List<BoardListDto> getList() {
        // db에서 가져온 원본데이터를 브라우저에 렌더링에 적합한 데이터로 변환
        return boardRepository.findAll().stream()
                .map(BoardListDto::new)
                .collect(Collectors.toList());
    }

    // 삭제 처리시 중간 처리
    public boolean delete(Long id) {
        return boardRepository.delete(id);
    }

    // 게시물 등록시 중간처리
    public boolean create(BoardSaveDto dto) {
        return boardRepository.save(dto.toEntity());
    }

    // 게시물 상세조회 중간처리
    public BoardDetailResponse getArticle(Long id) {
        Board foundBoard = boardRepository.findOne(id);
        return BoardDetailResponse.from(foundBoard);
    }

}