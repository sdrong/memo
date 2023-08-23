package Memo.Memo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


//AOP란 메서드의 실행 전, 후 또는 예외 방생시 특정 작업을 수행할 수 있도록 도와주는 프로그래밍 패러다임
@Component //이 클래스를 빈으로 등록
@Aspect //이 클래스가 Aspect역할을 수행함을 나타냄
public class TimeTraceAop {
    @Around("execution(* Memo.Memo..*(..))")//메서드 실행 전후에 실행될 advice 정의하는 어노테이션 Memo.Memo페키지 내의 모든 메서드 호출을 대상으로 aop 적용
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        //excute는 @Around 어노테이션과 함께 사용되어 메서드 실행 전후에 특정 작업을 수행함
        long start = System.currentTimeMillis();

        System.out.println("Start: " + joinPoint.toString());

        try{
            return joinPoint.proceed();
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;

            System.out.println("END: " + joinPoint.toString()+ " " + timeMs);
        }
    }
}
