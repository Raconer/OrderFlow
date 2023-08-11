# 주문 관리 시스템

## 기본 설명

### API
* API
  * POST /orders
    * 주문 접수처리 : 구매자가 주문 접수
  * GET /orders/{id}
    * 단일 주문조회
  * GET /orders
    * 주문 목록조회
  * PUT /orders
    * 주문 완료처리 : 판매자가 주문 완료
* 테스트
  * test.java.com.order.flow.OrdersControllerTest.java 에서 실행 및 결과 확인
    * 주문 접수 처리시 재고 부족시 : Exception 처리 (Random Data이다 보니 성공시 Check 만 현재 구현)
    * Multi Thread로 동시성 테스트 필요 -> // TODO
### Branch
* master
  * feature/** 개발 완료 된 Branch Core
* feature/**
  * 대문자
    * 기능 개발 용
    * ex) feature/GET_SINGLE_SEARCH, feature/POST_ORDERS
  * 소문자 
    * Core 기능 개발 및 기본 설정
    * ex) feature/set_query_dsl

###  Docker Mysql DB 설정
1. ```shell docker pull mysql```
   * Docker Mysql Image Pull
2. ```shell docker volume create mysql-volume```
   * Docker Volume 생성
   * Mysql DB 유지용
3. ```shell docker run -d --name mysql-container -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -v mysql-volume:/var/lib/mysql mysql```
   * Docker DB 실행
   * Mysql을 별도로 노트북에 설치 안하고 사용하여 3306으로 포트포워딩 사용했습니다.
   * MYSQL_ROOT_PASSWORD=root
     * 비밀번호 root 초기화

* Docker 사용시 setup.sql을 Volume Mount 하겠지만 아래의 databases를 만들어 주세요
```sql
    create database order
```

## Project 구조 및 설명
```shell
  src
  ├─main
  │  ├─ generated ## QueryDSL 파일 위치
  │  ├─ java.com.order.flow
  │  │                  ├─  common
  │  │                  │     ├─ exception
  │  │                  │     │    ├─  ControllerAdvice.java ## 중앙 Exception 처리
  │  │                  │     │    └─  ItemAlreadySoldException.java ## 재고 소진 Exception
  │  │                  │     └─ response
  │  │                  │           ├─  CommonRes.java  ## 공통 Response
  │  │                  │           ├─  ResponseMsg.java ## 기본 Response MSG
  │  │                  │           └─  ValidInfo.java ## Validate Exception Response
  │  │                  ├─  config
  │  │                  │     └─  QueryDSLConfig.java 
  │  │                  ├─  constant
  │  │                  │     └─  OrdersStatus.java ##  PENDING: 주문, COMPLETED : 주문 완료
  │  │                  ├─  controller
  │  │                  │     └─  OrdersController.java 
  │  │                  ├─  data
  │  │                  │     ├─  dto
  │  │                  │     │    ├─ PageDataDTO.java ## 페이지 포함 Response
  │  │                  │     │    ├─ PageDTO.java ## 페이지 조회 DTO
  │  │                  │     │    ├─  item
  │  │                  │     │    │   └─ ItemInsertDTO.java 
  │  │                  │     │    ├─order
  │  │                  │     │    │   ├─ OrdersDataDTO.java ## 주문 상세 DTO
  │  │                  │     │    │   ├─ OrdersInfoDTO.java ## 주문 목록 DTO
  │  │                  │     │    │   ├─ OrdersInsertDTO.java 
  │  │                  │     │    │   └─ OrderSuccessDTO.java
  │  │                  │     │    └─ orderItem
  │  │                  │     │        └─ OrderItemDataDTO.java ## 주문 ITEM 상세 DTO
  │  │                  │     └─entity
  │  │                  │        ├─ Common.java ## 공통 Entity
  │  │                  │        ├─ company
  │  │                  │        │    └─ Company.java ## 기업 
  │  │                  │        ├─ item
  │  │                  │        │    └─ Item.java ## 아이템 목록
  │  │                  │        ├─ order
  │  │                  │        │    └─ Orders.java ## 주문 목록
  │  │                  │        ├─ orderItem
  │  │                  │        │    └─ OrdersItem.java ## 주문 -> 아이템 
  │  │                  │        └─users
  │  │                  │             └─ Users.java ## 사용자 목록 : company_id != null ? 판매자 : 구매자
  │  │                  ├─  repository 
  │  │                  │  ├─ item
  │  │                  │  │  │  ItemRepository.java 
  │  │                  │  │  └─  impl
  │  │                  │  │       └─ ItemRepositoryImpl.java ## Item QueryDSL
  │  │                  │  ├─ order
  │  │                  │  │  ├─ OrdersRepository.java
  │  │                  │  │  ├─ impl
  │  │                  │  │  │  └─ OrdersRepositoryImpl.java  ## Order QueryDSL
  │  │                  │  │  └─ orderImpl  ## 맵핑 테스트를 위한 폴터 (확인 불필요)
  │  │                  │  │     └─ OrderRepositoryImpl.java ## 추후 맵핑 DTO 맵핑 
  │  │                  │  └─orderItem
  │  │                  │          OrdersItemRepository.java
  │  │                  ├─ service
  │  │                  │     ├─ ItemService.java
  │  │                  │     ├─ OrdersItemService.java
  │  │                  │     ├─ OrdersService.java
  │  │                  └─ OrderFlowApplication.java
  │  └─ resources
  │       ├─ application.yml
  │       └─ data.sql ## Seed Data
  └─  test
      └─  java.com.order.flow
                           ├─ controller
                           │     └─ OrdersControllerTest.java
                           └─ testData
                                 └─ OrderTestData.java ## 테스트 용 데이터
```
## Build Spec
* JDK
  * temurin jdk 20
* FrameWork
  * Spring Boot 3.1.2
  * spring-boot-starter-validation
  * LomBok
* DB
  * Mysql
  * JPA:3.1.2
  * QueryDSL
* TEST
  * datafaker:2.0.1
    * 테스트 데이터 용
  * spring-boot-starter-test

# 후기
0. fetch join

> QueryDSL를 사용할때 Join을 사용할때 N+1을 방지 하기 위해 fetch Join을 사용하였는데 
> 결과를 보니 fetch join 을 사용하지 않았을때랑 같은 결과가 출력 되었다.<br>
> 이를 확인해 보니
> 주어진 코드에서 fetch join을 사용하지 않았지만, Projections.constructor()를 사용하여 OrderItemDataDTO를 생성하는 과정에서 필요한 필드들이 모두 선택되었기 때문에 join만 사용하여도 해당 필드들을 로딩할 수 있다는 내용을 확인했다.<br>
> 결국엔 fetch join만 사용한다고 모든 문제가 해결되는건 아니다.

1. JPA Bulk Insert -> // TODO
> JPA를 사용 해서 SaveAll을 사용하여 Bulk Insert를 할때가 있다.<br>
> application.yml 에서 spring.jpa.properties.hibernate.jdbc.batch_size: 100 를 정해서
> 100개 단위로 Bulk Insert 및 Select 등을 할수 있다.<br>
> 하지만 @GeneratedValue(strategy = GenerationType.IDENTITY) 를 사용하게 되면 <br>
> Bulk Insert가 적용되지 않는다.<br>
> 이를 해결하기 위해 ID를 UUID나 별도의 Plan을 적용 해야 한다.<br>

2. QueryDSL Mapping -> // TODO
> QueryDSL 을 사용하면 가독성과 쉽게 원하는 컬럼을 출력 할수 있다. <br>
> 하지만 DTO에 맵핑하기 위해서 한가지 문제가 생긴다. <br>

```java
 public class Order {
    private Long id;
    private List<OrdersItem> orderItemList;
 }

 public class OrdersItem {
    private Long id;
    private String name;
 }
```

> 위와 같은 경우에 맵핑이 되지 않는다 <br>
> Projections.constructor, Projections.list,  Projections.bean 등 을 사용하였지만 원하는 결과가 나오지 않는다.
> fetchOne를 사용하면 출력은 다수인데 fetchOne를 사용해서 에러가 난다.<br>
> 참고_1 : [querydsl dto mapping & subquery](https://www.inflearn.com/questions/750129/querydsl-dto-mapping-amp-subquery-%EC%A7%88%EB%AC%B8-%EB%82%A8%EA%B9%81%EB%8B%88%EB%8B%A4)<br>
> 참고_2 : [Dto안에 List<Dto> 조회하기](https://www.inflearn.com/questions/188618/dto%EC%95%88%EC%97%90-list-lt-dto-gt-%EC%A1%B0%ED%9A%8C%ED%95%98%EA%B8%B0)<br>
> 위와 같은 내용 들만 있다.<br>
> .transform을 사용하면 된다고 하지만 현재로는 생각처럼 되지 않았다.<br>
> 지금 당장 바로 해야 한다면 mapstruct-processor를 사용하겠지만 좀 더 쉬운 방법이 있을거라 생각이든다.

3. 격리 수준 -> // TODO

> 데이터를 Insert 할때 동시성을 방지 할떄 격리 수준을 높이 거나 낙과적인 락을 사용한다.<br>
> JPA에서 낙관적인 락을 설정 하기 위해 @Version을 사용한다.<br>
> 하지만 지금은 기본 설정을 기준으로 하였기 때문에  isolation = Isolation.READ_COMMITTED 으로 설정하여 이전 데이터가 Commit이 끝날때 까지 데이터가 변경되지 않도록 설정 하였다.<br>
> 이러면 DB에 부담이 가는 설정이므로 @Version으로 관리를 하는 방식으로 변경 예정이다.
> 참고_1 : [DB 격리 수준](https://raconer.tistory.com/entry/DB-%EA%B2%A9%EB%A6%AC-%EC%88%98%EC%A4%80ACID-Isolation)<br>
> 참고_2 : [낙관적인 락 & 비관적인락](https://raconer.tistory.com/entry/%EB%82%99%EA%B4%80%EC%A0%81%EC%9D%B8%EB%9D%BDOptimistic-Locking-%EB%B9%84%EA%B4%80%EC%A0%81%EC%9D%B8Pessimistic-Locking)