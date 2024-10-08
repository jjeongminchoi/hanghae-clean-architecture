# ERD

<img width="715" alt="image" src="https://github.com/user-attachments/assets/f7b2ef96-ba23-4b09-bd7b-080adc58fdf2">

## 설계 개요
사용자가 특강 신청을 하는 프로그램으로
Users, Lecture, LectureManager, Reservation 총 4가지 도메인으로 구성하였습니다.

## 특징
1. 동시성 문제 해결을 위한 정규화
  - 사용자가 같은 특강에 대해 동시에 신청하는 상황에 대비하여, 변경이 잦은 값을 분리했습니다.
  - 예를 들어, Lecture 엔티티에서 관리하던 capacity(남은 신청 수)는 자주 변경되는 값이므로 LectureManager로 분리하여 관리하였습니다.
    이렇게 분리함으로써 잦은 데이터 갱신에도 성능 저하를 최소화하고, 조회 시 일관성을 유지할 수 있도록 설계했습니다.
2. 엔티티 간 직접적인 연관 관계 미설정
  - 엔티티 간 연관 관계를 직접 맺지 않음으로써 설계를 유연하게 유지했습니다.
  - 점차 IT 중심으로 세상의 모든 것들을 전상상에 녹이기 시작했습니다.
    따라서 변화와 복잡도가 점차 증가하는 추세로부터 엔티티 간 의존성을 최소화하여 변경에 유연하게 대응할 수 있는 구조를 선택했습니다.
  - 연관 관계를 직접 맺지 않음으로써 독립적인 확장과 특정 엔티티의 변경이, 다른 엔티티에 미치는 영향을 줄일 수 있게 되었습니다.
