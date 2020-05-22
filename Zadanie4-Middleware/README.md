Zadanie składa się z dwóch części.

# Zadanie 4.1

Wynikiem prac ma być aplikacja klient-serwer, w której komunikacja rozproszona jest zrealizowana z wykorzystaniem technologii gRPC. Klient powinien dokonywać subskrypcji na zdarzenia. To, o czym mają one informować, jest w gestii Wykonawcy, np. o nadchodzącym wydarzeniu lub spotkaniu, którym jesteśmy zainteresowani, o osiągnięciu określonych w żądaniu warunków pogodowych w danym miejscu, itp. Na dane zdarzenie może się naraz zasubskrybować wielu odbiorców i może istnieć wiele niezależnych subskrypcji (tj. np. na wiele różnych instancji spotkań).  Należy odpowiednio wykorzystać mechanizm strumieniowania (stream). Wiadomości mogą przychodzić z dowolnymi odstępami czasowymi  (nawet bardzo długimi), jednak na potrzeby demonstracji rozwiązania należy przyjąć interwał rzędu pojedynczych sekund).

W definicji wiadomości przesyłanych do klienta należy wykorzystać pola enum, string, ew. message - wraz z co najmniej jednym modyfikatorem repeated. Etap subskrypcji powinien w jakiś sposób parametryzować otrzymywane wiadomości (np. obejmować wskazanie miasta, którego warunki pogodowe nas interesują_.

Dla uproszczenia realizacji zadania można (nie trzeba) pominąć funkcjonalność samego tworzenia instancji wydarzeń lub miejsc, których dotyczy subskrypcja i notyfikacja - może to być zawarte w pliku konfiguracyjnym, a nawet kodzie źródłowym strony serwerowej. Treść wysyłanych zdarzeń może być wynikiem działania bardzo prostego generatora.

W realizacji należy zadbać o odporność komunikacji na błędy sieciowe (które można symulować czasowym wyłączeniem klienta lub serwera lub włączeniem firewalla). Ustanie przerwy w łączności sieciowej musi pozwolić na ponowne ustanowienie komunikacji bez konieczności restartu procesu. Rozwiązanie musi być także "PAT-friendly" (tj. uwzględniać rozważane na laboratorium sytuacje). 

# Zadanie 4.2

Wynikiem prac ma być aplikacja klient-serwer, w której komunikacja rozproszona jest realizowana w technologii ICE albo Thfitt (do wyboru). Aplikacja ma pozwalać na sterowanie urządzeniami tzw. inteligentnego domu, na którego wyposażeniu znajdują się różne urządzenia, np. czujniki temperatury czy zdalnie sterowane lodówki, piece, kamery monitoringu z opcją PTZ (pan/tilt/zoom), bulbulatory, itp. Każde z urządzeń może występować w kilku nieznacznie się różniących odmianach, a każda z nich w pewnej (niewielkiej) liczbie instancji. Dom ten nie oferuje obecnie możliwości budowania złożonych układów, pozwala użytkownikom jedynie na sterowanie pojedynczymi urządzeniami oraz odczytywanie ich stanu.
