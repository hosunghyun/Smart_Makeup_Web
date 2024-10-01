class User:
    def __init__(self, name=0):  # 초기값을 0으로 설정
        self._name = name  # private 변수

    # Getter
    @property
    def name(self):
        return self._name

    # Setter
    @name.setter
    def name(self, name):
        self._name = name

# 예시 사용
user1 = User()  # user1의 _name은 0
print(user1.name)  # 0 출력

user1.name = "Alice"  # 이름을 "Alice"로 설정
print(user1.name)  # "Alice" 출력

user2 = User("Bob")  # user2의 _name은 "Bob"
print(user2.name)  # "Bob" 출력
