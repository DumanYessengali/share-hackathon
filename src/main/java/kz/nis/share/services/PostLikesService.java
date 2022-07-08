package kz.nis.share.services;

import kz.nis.share.repositories.PostLikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikesService {
    private final PostLikesRepository postLikesRepository;
}
