
# 스트림 (Stream)
- `Stream`이란 어떤 시퀀셜하거나 병렬적인 오퍼레이션을 제공하는 element들의 연속 이라고 할 수 있다. 
    - 콜렉션이나 어떤 연속된 데이터를 처리하는 오퍼레이션들의 모음
- `Collection`은 데이터를 저장하고 있는 저장소지만, Stream은 그 자체가 데이터나 저장소는 아니다.
    - Collection은 자체가 데이터를 가지고 있는 것이고, 스트림은 그것을 소스로 써서 어떤 처리를 하는 것이다.
    - 즉 Stream은 데이터를 담는 저장소가 아니다.
- _Functional in nature_
    - 원래 소스를 변경하지 않는다는 의미이다.
    - 어떠한 Stream을 얻어와 오페레이션을 수행하면 그 결과는 Stream이고, 원본 데이터는 변경되지 않는다.
    > 리턴값이 Stream이라는 건 중개 오퍼레이션의 경우. 터미널 오퍼레이션은 Stream이 반환되는건 아니다.

> ### 중개 오퍼레이션? 터미널 오퍼레이션?  
> Stream API에는 크게 두 가지 종류가 있다.    
> - `중개 오퍼레이션(Intermediate Operation)`
>   - Stream을 리턴하기 때문에 계속 이어서 Stream API를 사용할 수 있는 종류의 메서드다.
>   - map() 같은 중개형 오퍼레이터를 사용하고 `.`을 찍으면 다른 Stream API를 사용할 수 있다.
> - `종료형 오퍼레이션(Terminate Operation)`
>   - Stream을 종료시키는 오퍼레이션이다. 
>   - 중개형 오퍼레이션과 가장 큰 차이는 Stream이 아닌, **다른 타입을 리턴한다는 것이다.**

> #### Stream을 비유하자면  
> Collection 이라는 박스에 담긴 원소들을 하나씩 복사해서 컨베이어 벨트에 올린다.  
> 복사된 원소들에 어떠한 가공을 한다.  
> 그리고 그 가공된 원소들을 박스에 담는다. 
> 컨베이어 벨트가 스트림이다.

- Stream은 데이터를 한번만 거치고 지나간다. (컨베이어 벨트처럼)
- 무제한/실시간으로 넘어오는 데이터를 처리할 수 있고, `Short Circuit` 메소드를 사용해서 그 수를 제한할 수 있다. 
- `중개(intermediate) 오퍼레이션`은 근본적으로 lazy 하다.

> #### lazy 하다?
> `스트림 파이프라인` 이라는 컨베이어 벨트에는 중개 오퍼레이터를 0개 또는 여러개 올릴 수 있다.   
> 중개 오퍼레이터가 오던. 오지 않던 중요한건 반드시 `종료형 오퍼레이터`가 와야 실행된다.  
> 종료형 오퍼레이터가 오기전까지 중개 오퍼레이터는 무의미하다.  
> 예를 들어, map() 메소드를 사용하고 종료형 메소드를 호출하지 않으면, map() 메소드의 내용이 실행되지 않는다.  
> 왜냐하면 중개형은 종료형이 오기전에는 처리를 하지 않기 때문이다.

- 손쉽게 병렬처리를 할 수 있다.
스트림을 안쓰고 병렬처리를 할땐 
    - ```java
        for (String name: names) {
            if (name)...멀티쓰레드..
        }
      ```
    - 이렇게 하는데, 이런 작업을 병렬적으로 처리하기 어렵다.
    - 스트림같은 경우 `Parallel stream`을 받아서 (jvm이 알아서) 병렬적으로 처리할 수 있다.
    - 실제로 쓰레드 이름을 찍어보면.. 쓰레드가 다른것을 알 수 있다.
  
> #### 병렬 처리를 쓴다고 빠르진 않다. 오히려 느려지기도 한다.
> 쓰레드를 만들어서 처리하는 것에도 비용이 들기 때문이다. (만들고 수집하고 컨텍스트 스위칭 비용 등)
> 유용할때는 데이터가 정말 방대하게 큰 경우다. 대부분의 경우는 그냥 스트림을 써도 된다.
> 등등 케이스마다 다르니까 성능 측정해서 효과가 있는지를 확인해보는게 좋다.


## 스트림 API
- filter(), map() 등은 생략
- `flatMap()`
```
List<List<OnlineClass>> keesunEvents = new ArrayList<>();
keesunEvents.add(springClasses);
keesunEvents.add(javaClasses);
``` 
이렇게 2차원 리스트가 있고, springClasses 라는 리스트와 javaClasses 라는 리스트가 저장되있다.  
만약 두 클래스의 이름들을 뽑아온 스트림을 만들고 싶다면?  

**keesunEvents**로 stream을 만들면 stream에는 **springClasses**의 List들이 element로 존재한다.
`flatMap()`을 사용하면 이 List들을 flat하게 만들어준다.  
flat하게 만들어 준다는 것은 리스트로 묶여있는 것을 풀어준다는 것이다.  

예를들어 **keesunEvents**라는 리스트 안에 **springClasses**라는 리스트가 있고, 그 리스트 안에는 **Java7, Java8** 이라는 **OnlineClass** 라는 타입의 데이터들이 있다.
여기서 **keesunEvents**에 `flatMap()`을 적용시키면 **springClasses**의 List들이 아닌, List들 안에 있는 데이터들을 꺼내서 나열시켜버린다.

```java
        keesunEvents.stream()
//              .flatMap(list -> list.stream())
                .flatMap(Collection::stream) // 메소드 레퍼런스
                .forEach(oc -> System.out.println(oc.getId())); // OnlineClass 타입이 온다.
```

- 몇 회는 지나치고, 몇 회까지로 제한하는 방법은 `iterate()`를 사용하면 된다.
  ```java
  Stream.iterate(10, i -> i + 1)
  .skip(10)
  .limit(10)
  .forEach(System.out::println);
  ```

> 💡**Tip**
> OnlineClass가 isClosed()라는 메소드를 가지고 있는데, 메소드 레퍼런스를 쓰면 닫혀있나? -> true밖에 확인하지 못한다.  
> 즉, 닫혀있다는 대답은 들을 수 있는데, 열려있다는 대답은 못듣는다. (`stream.filter(OnlineClass::isClosed))` 를 반대로 표현하고 싶다는 의미)
> 
> 이럴때, 자바11부터 제공하는 `Predicate.not()` 이라는 스태틱 메소드를 사용하면 된다.
> `stream.filter(Predicate.not((OnlineClass::isClosed)))`