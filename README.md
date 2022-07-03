# TODO
### DTOの括り方(UseCaseに用意するかどうか)

例えばGet系UseCaseの返り値でも

```kotlin
Project to List<Feature>
Feature to List<Task>
```

等が考えられる
本来UseCaseごとにDTOを用意するべきだが、重複を避けるために現在１つにまとめている
そのため、上のような形を採用する場合、それぞれにDTOを用意する形になる

### QueryServiceの処理を変更する(DTOの括り方により変更)

現在はDomainをそのまま返しているが、本来はDTOに合わせた返り値にすべきである。
そもそもQueryServiceが必要かどうかも合わせて検討する必要がある
