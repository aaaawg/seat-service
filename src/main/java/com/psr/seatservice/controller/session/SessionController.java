package com.psr.seatservice.controller.session;
import com.psr.seatservice.SessionConst;
import com.psr.seatservice.domian.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
@Slf4j
@RestController
public class SessionController {
    //로그인 성공 및 확인 위해 잠시...
    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "세션이 없습니다.";
        }
        //세션 데이터 출력
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}",
                        name, session.getAttribute(name)));
        System.out.println("sessionId="+ session.getId());
        log.info("maxInactiveInterval={}", session.getMaxInactiveInterval());
        log.info("creationTime={}", new Date(session.getCreationTime()));
        log.info("lastAccessedTime={}", new
                Date(session.getLastAccessedTime()));
        System.out.println("isNew="+ session.isNew());
        User logUser = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);

        return logUser.getName();
    }
}