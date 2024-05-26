package kr.co.example.service;

import kr.co.example.Repository.ArticleRepository;
import kr.co.example.Repository.UserRepository;
import kr.co.example.dto.ArticleDTO;
import kr.co.example.dto.PageRequestDTO;
import kr.co.example.dto.PageResponseDTO;
import kr.co.example.dto.UserDTO;
import kr.co.example.entity.Article;
import kr.co.example.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    // 회원 가입
    public ResponseEntity<?> registerUser(UserDTO userDTO) {
        String encoded = passwordEncoder.encode(userDTO.getUserPw());
        userDTO.setUserPw(encoded);
        userDTO.setUserRole("USER");

        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(user);

        if (savedUser.getUserId() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

    // 회원 목록
    public ResponseEntity<?> selectUserList() {

        List<User> userList = userRepository.findAll();

        List<UserDTO> userDTOList = userList.stream()
                .map(user ->  modelMapper.map(user, UserDTO.class))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(userDTOList);
    }

}
