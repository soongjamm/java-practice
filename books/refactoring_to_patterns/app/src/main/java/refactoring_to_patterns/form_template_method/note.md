### 템플릿 메서드 패턴

책에서는 특정 '상속구조' 내에서 '유사한 작업'을 '같은 순서' 로 실행하는 메서드를 각각 구현하고 있을 때 사용한다고 말한다.

중요한 것은 불변적인 부분을 한번만 구현하는 것이다.  
경험에 비추어보아 이게 불변이라는 점이 중요하다.  

과거에 도메인 코드에서 중복되는 상속구조를 발견하고는 이를 템플릿 메서드로 리팩터링한 적이 있다.  
리팩토링의 동기는 `코드의 중복`이었다. 같은 코드가 여러 클래스에서 반복되고 있었고, 각 메소드에서 동일한 작업들을 하고 있었다.  
그 작업들 중간에 의존하는 대상만 살짝 다를 뿐이었다.  

하지만 리팩토링은 실패나 마찬가지였다. 왜냐하면 그 도메인 코드들은 변경될 여지가 있었기 때문이다.  
변할 여지가 있는데, 템플릿 및 상속 구조로 딱딱하게 변하엿고, 변경하기가 어려워졌기 때문이다. 


그렇다면 언제 템플릿 메서드 패턴을 적용해야 할까? 불변이라는 것을 어떻게 판단할 수 있을까?  
- 공식, 법칙, 이론 등 통념적인 개념을 담고있는 도메인 
- 유틸리티성 코드

반대로 불변이 아닌 것은?
- '정책' 을 담고 있는 도메인

정도라고 일단 생각이 난다.