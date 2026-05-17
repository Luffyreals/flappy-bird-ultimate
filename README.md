# Flappy Bird Ultimate

## 1. About

**Tên Dự Án:** Flappy Bird Ultimate
**Link Dự Án:** [GitHub Link](https://github.com/Luffyreals/flappy-bird-ultimate)

### Thành viên

* **[Nguyễn Hoàng Tùng]** — *Lead Developer / UI-UX Designer*
    GitHub: https://github.com/Luffyreals
    Contact: 0906279876
* **[Trần Quốc Việt]**
* **[Nguyễn Trường Bảo Hoàng]**
* **[Hoàng Anh Quân]**
* **[Nguyễn Văn Hiếu]**
* **[Đào Bích Ngọc]**

### Mô hình làm việc

Dự án áp dụng phương pháp **Scrumban** (kết hợp linh hoạt giữa Scrum và Kanban) nhằm tối ưu hóa tiến độ lập trình và quản lý các tác vụ UI/UX theo thời gian thực. Các đầu việc được phân rã, theo dõi liên tục thông qua hệ thống quản lý trực quan, giúp duy trì các chu kỳ tối ưu hóa ngắn (Polishing Sprints) nhằm liên tục nâng cấp cảm giác tương tác và sửa đổi các lỗ hổng vận hành kỹ thuật.

Do giới hạn thời gian nghiên cứu và thực nghiệm công nghệ đồ họa máy tính trên môi trường Desktop, phiên bản hiện tại đóng vai trò là một bản **Technical Demo hoàn chỉnh**, hiện thực hóa trọn vẹn mô hình vòng lặp game (Game Loop) tiêu chuẩn cùng hạ tầng lưu trữ dữ liệu nâng cao.

### Chiến lược quản lý mã nguồn

Nhóm áp dụng mô hình cấu trúc **Gitflow** nghiêm ngặt để tổ chức và kiểm soát các phiên bản mã nguồn của hệ thống. Mỗi tính năng mới hoặc các đợt sửa lỗi UI đều được triển khai trên các nhánh độc lập tách ra từ `develop` theo quy ước đặt tên `feature/ten-chuc-nang` hoặc `fix/ten-loi`. Sau khi hoàn tất kiểm thử cục bộ, một **Pull Request (PR)** sẽ được khởi tạo để rà soát chất lượng mã nguồn (Code Review) trước khi chính thức hợp nhất (Merge) vào nhánh phát triển chung.

Các nhánh chính trong kiến trúc:
* `main`: Chứa phiên bản sản phẩm ổn định cao nhất, đã trải qua kiểm thử toàn diện và sẵn sàng đóng gói phát hành.
* `develop`: Nhánh tích hợp tài nguyên đồ họa, âm thanh và mã nguồn mới nhất sau khi đã thông qua rà soát cơ bản.
* `feature/*` hoặc `fix/*`: Các nhánh chức năng hoặc sửa lỗi ngắn hạn, được cô lập để phát triển độc lập và giải phóng ngay sau khi hợp nhất.

---

## 2. Giới thiệu dự án

**Flappy Bird Ultimate** là một trò chơi thuộc thể loại **2D Retro Arcade Remake**, được phát triển trên nền tảng **Java (JDK 17+)** và framework **LibGDX**. 

Khác với các phiên bản Flappy Bird thông thường, dự án hướng tới việc **xây dựng một bộ khung Engine UI/UX chuẩn mực**, giải quyết triệt để các vấn đề về co giãn màn hình đa độ phân giải, đồng bộ âm thanh live đa luồng, và thiết lập hệ thống thống kê chỉ số người chơi chuyên sâu nhằm tối ưu hóa phản hồi xúc giác và thị giác trên môi trường Desktop.

### Đặc điểm cốt lõi

* **Virtual Resolution Scaling Engine:** Cơ chế xử lý hiển thị cố định không gian hình học chuẩn **Full HD (1920x1080)** thông qua giải thuật `FitViewport`, tự động căn giữa bản đồ và dựng viền đen cân đối khi kéo giãn cửa sổ.
* **Live Adjust Audio Mixing:** Hệ thống phân tách và trộn âm thanh thời gian thực, cho phép nhạc nền (BGM) chạy liền mạch toàn cục và cập nhật biên độ phát của hiệu ứng (SFX) động theo cấu hình hệ thống.
* **Silent Local Preferences Save:** Hạ tầng lưu trữ ngầm hoạt động liên tục, tự động ghi nhận toàn bộ hành trình trải nghiệm và thiết lập hệ thống vào file save vật lý trên ổ cứng, ngăn chặn việc mất dữ liệu khi tắt ứng dụng.
* **Text Anti-Aliasing (Khử răng cưa):** Kỹ thuật can thiệp vào bộ lọc kết cấu hình ảnh `Linear Filter` của Font hệ thống, giúp toàn bộ giao diện chữ lớn luôn mềm mại, sắc nét trên màn hình Full HD.

### Mục tiêu phát triển

Dự án đóng vai trò như một môi trường thực nghiệm chuyên sâu nhằm làm chủ kiến trúc framework LibGDX và chuẩn hóa quy trình phát triển game PC.

#### a. Mục tiêu kỹ thuật
* Áp dụng **State Machine Pattern** để phân tách trạng thái vật lý và giao diện trò chơi thành các luồng xử lý riêng biệt.
* Hiện thực hóa mẫu thiết kế **Return Pattern (Context-safe)** trong lập trình UI, cho phép một màn hình cài đặt có thể tái sử dụng trong nhiều ngữ cảnh khác nhau.
* Quản lý vòng đời tài nguyên (Asset Lifecycle) sạch sẽ, giải phóng hoàn toàn bộ nhớ RAM và VRAM khi chuyển cảnh thông qua hàm `dispose()` để chống tràn bộ nhớ (Memory Leak).

#### b. Mục tiêu quy trình và phát triển
* **Quy chuẩn hóa giao diện hình học:** Tính toán và khóa chặt tọa độ các thành phần phụ trợ (Panel, Button) theo một tỷ lệ đối xứng duy nhất, tạo ra trải nghiệm thị giác đứng im vững chãi.
* **Hiện thực hóa logic vật lý chuẩn xác:** Chuyển hóa các công thức gia tốc trọng lực và xung lực nhảy thời gian thực thành mã nguồn logic ổn định.
* **Lập trình hướng cấu trúc sạch:** Tổ chức cấu trúc Package rõ ràng, tách biệt giữa thực thể vật lý (`sprites`), màn hình hiển thị (`screens`) và bộ nạp tài nguyên (`tools`).

---

## 3. Các Chức Năng Chính

Dự án được tổ chức thành các hệ thống độc lập hoạt động tuần hoàn và tương tác chặt chẽ với nhau:

### 3.1. Hệ thống quản lý màn hình toàn cục (ScreenManager)
* **Cơ chế hoạt động:** Ủy quyền điều hướng tập trung từ Class gốc thông qua lệnh `game.setScreen()`, cho phép giải phóng hoàn toàn màn hình cũ và nạp màn hình mới lên RAM.
* **Luồng điều hướng:** Hỗ trợ mạch chuyển cảnh mượt mà từ `MenuScreen` sang các phân cảnh phụ (`Tutorial`, `Achievement`, `Setting`) hoặc kích hoạt trực tiếp không gian màn chơi `PlayScreen`.

### 3.2. Cơ chế vật lý & Tuần hoàn ống nước vô hạn
* **Mô phỏng vật lý (`Bird.java`):** Chú chim vận hành dựa trên trọng lực tích lũy `GRAVITY (-18f)` kéo xuống liên tục và xung lực ngược hướng `JUMP_VELOCITY (550f)` kích hoạt ngay khi nhận tín hiệu từ phím Space hoặc chuột trái. Vùng va chạm được cấu hình bằng một đường tròn (`boundingCircle`) bán kính 20 pixels nằm đồng tâm vật lý.
* **Mô-đun ống nước (`Pipe.java`):** Quản lý cặp ống trên/dưới có độ rộng cố định 130px và khoảng trống an toàn 320px. Ống trên tự động lật ngược kết cấu đồ họa theo trục Y bằng tham số `flipY = true` để đảm bảo tính thẩm mỹ.
* **Hệ thống mảng tuần hoàn (`PipeHandler.java`):** Chỉ khởi tạo cố định **5 cặp ống** trên bộ nhớ RAM. Khi bất kỳ cặp ống nào trôi khuất hẳn sang mép trái màn hình (`X + TUBE_WIDTH <= 0`), hệ thống lập tức tái định vị (`reposition`) đẩy nó ra phía sau đuôi cặp ống xa nhất bên phải với tọa độ Y được tính toán random mới. Cơ chế này giúp trò chơi chạy vô hạn mà hoàn toàn không tốn thêm tài nguyên phần cứng.

### 3.3. Máy trạng thái trận đấu tập trung (Game State Machine)
* **Trạng thái PLAYING:** Kích hoạt toàn bộ luồng update vật lý của chim, trôi ống nước, tính toán điểm số và lắng nghe input điều khiển.
* **Trạng thái PAUSED:** Khóa cứng toàn bộ chuyển động vật lý và tiến trình trôi của ống nước. Đồng thời, hệ thống dựng đè một thực tại menu phụ gồm 3 tùy chọn (*Resume, Setting, Main Menu*) lên trên khung cảnh đang chơi dở.
* **Trạng thái GAME_OVER:** Ngắt toàn bộ luồng update vị trí của chú chim và ống nước để giữ nguyên hiện trường va chạm. Hệ thống mở khóa tính năng hồi sinh nhanh bằng phím tắt `R` hoặc rút lui về menu chính bằng phím `ESC`.

### 3.4. Hệ thống thống kê & Phân bậc danh hiệu (Achievement Engine)
* **Thu thập chỉ số dữ liệu:** Đọc và tính toán động toàn bộ lịch sử chơi game của người dùng bao gồm: Kỷ lục điểm số (`highScore`), Tổng số ván đã chơi (`totalGames`), Tổng số điểm tích lũy (`totalScore`), và Tổng số lần đập phím vỗ cánh (`totalFlaps`). Từ đó tự động trích xuất chỉ số kỹ năng nâng cao là Điểm số trung bình trên mỗi ván (`avgScore`).
* **Phân bậc danh hiệu (Medal Logic):** Tự động so khớp kỷ lục cá nhân để cấp các bậc huy chương tương ứng bằng font chữ đổi màu thẩm mỹ: `BRONZE` (>=10 điểm), `SILVER` (>=20 điểm), `GOLD` (>=30 điểm), và bậc cao nhất là `PLATINUM` (>=40 điểm).

### 3.5. Hệ thống cấu hình âm lượng Live luồng kép
* **Đồng bộ Live âm thanh:** Cung cấp giao diện tăng giảm âm lượng trực quan theo từng nấc 10% cho cả Nhạc nền (BGM) và Hiệu ứng (SFX). Nhạc nền sẽ thay đổi độ lớn nhỏ ngay lập tức khi điều chỉnh, và hiệu ứng nhảy sẽ tự động phát thử một nhịp tương ứng với mức volume mới để người chơi thẩm định.
* **Cơ chế lưu trữ Preference:** Sau khi người chơi nhấn nút quay lại, hệ thống tự động đồng bộ hai chỉ số volume mới xuống file save cục bộ và ép bộ nạp tài nguyên áp dụng biên độ phát này cho toàn bộ tiến trình game tiếp theo.

---

## 4. Công nghệ

### 4.1. Công nghệ sử dụng
* **Java (JDK 17+):** Ngôn ngữ phát triển chính, khai thác mô hình lập trình hướng đối tượng (OOP) để quản lý cấu trúc Screen và Sprites sạch sẽ.
* **LibGDX (v1.12.1+):** Framework mã nguồn mở chuyên dụng, cung cấp toàn bộ API xử lý đồ họa `SpriteBatch`, âm thanh `Music`/`Sound` và quản lý Camera.
* **LWJGL 3:** Bộ thư viện đồ họa máy tính hạng nặng (Lightweight Java Game Library) làm backend giao tiếp phần cứng, xử lý FPS ổn định tại mốc 60 và quản lý cửa sổ hiển thị trên Windows/macOS/Linux.
* **Gradle:** Công cụ quản lý dự án tự động, chịu trách nhiệm nạp thư viện dependencies và đóng gói ứng dụng đa module.

### 4.2. Quản lý dữ liệu & Bộ nhớ cục bộ
* **LibGDX Preferences:** Định dạng lưu trữ dữ liệu có cấu trúc (Key-Value) dưới dạng file XML lưu thẳng vào thư mục hệ thống của ổ cứng. Sử dụng lệnh `.flush()` để đóng gói và đồng bộ luồng ghi dữ liệu an toàn.
* **GPU Filtering Connection:** Tận dụng hàm `.setFilter()` cấu hình bộ lọc kết cấu `TextureFilter.Linear` trực tiếp trên RAM và card đồ họa, ép hệ thống tính toán nội suy làm mịn các cạnh pixel của chữ khi thực hiện phóng to quy mô.
* **VRAM Data Cleaning:** Triển khai cơ chế giải phóng tài nguyên đồ họa nghiêm ngặt thông qua lệnh `.dispose()` cho các đối tượng `BitmapFont`, `Texture` và `SpriteBatch` để làm sạch bộ nhớ card đồ họa khi tắt Screen hoặc đóng game.

### 4.3. Kiến trúc phần mềm
* **State Pattern:** Tổ chức luồng logic theo máy trạng thái tập trung nhằm phân tách các hành vi tương tác chuột và phím của người chơi theo từng bối cảnh cụ thể.
* **Context Return Pattern:** Thiết lập cấu trúc hàm khởi tạo nhận tham số là một đối tượng `Screen previousScreen`. Kỹ thuật này giúp giải phóng hoàn toàn bộ nhớ của màn hình Setting khi nhấn nút BACK và trả người dùng quay lại chính xác không gian MenuScreen hoặc PlayScreen đang chơi dở trước đó.
* **Viewport Virtual Resolution Architecture:** Áp dụng mô hình thiết kế không gian hình học ảo cố định độ rộng `1920` và độ cao `1080` phối hợp camera ma trận `camera.combined`. Khi cấu hình hàm `resize(width, height, true)`, FitViewport sẽ tự động tính toán lại tỷ lệ co giãn vật lý để đảm bảo game luôn hiển thị đúng tỷ lệ khung hình chuẩn.

---

## 5. Các vấn đề gặp phải & Giải pháp khắc phục

### 5.1. Hiện tượng chú chim rơi xuyên thấu ống nước khi va chạm
* **Vấn đề:** Khi va chạm xảy ra, game lập tức chuyển sang trạng thái `GAME_OVER`. Tuy nhiên, do hàm `bird.update(delta)` vẫn tiếp tục được triệu gọi bên dưới khối logic, chú chim vẫn chịu tác động của trọng lực và rơi thẳng tắp xuống đất, xuyên qua cả bề mặt vật lý của phần ống nước bên dưới, phá vỡ cảm giác chân thực.
* **Hành động giải quyết:** Tiến hành can thiệp vào cấu trúc hàm `update(float delta)` của lớp `PlayScreen.java`. Cô lập dòng lệnh cập nhật vị trí của chú chim, chỉ cho phép thực thi bên trong khối điều kiện `State.PLAYING`. Khi trạng thái chuyển sang `GAME_OVER`, toàn bộ lệnh update vật lý của chim bị loại bỏ hoàn toàn.
* **Kết quả:** Ngay khi mỏ chú chim chạm vào rìa ống nước, toàn bộ không gian hình học sẽ đóng băng ngay lập tức, giữ nguyên hiện trường va chạm rõ ràng cho người chơi.

### 5.2. Cửa sổ game bị đẩy tràn viền và biến mất thanh tiêu đề Title Bar trên Windows
* **Vấn đề:** Khi khởi tạo kích thước cửa sổ vật lý ban đầu đúng bằng độ phân giải tối đa của màn hình máy tính (1920x1080) trong file cấu hình Launcher, hệ điều hành Windows sẽ tự động kích hoạt chế độ Borderless khiến thanh công cụ chứa các nút thu nhỏ, phóng to và nút tắt [X] bị đẩy văng ra ngoài mép màn hình.
* **Hành động giải quyết:** Tiến hành điều chỉnh hạ kích thước cửa sổ vật lý lúc khởi tạo ban đầu xuống mốc `1280x720` thông qua dòng lệnh `config.setWindowedMode(1280, 720)` trong file `DesktopLauncher.java`. Đồng thời bổ sung cấu hình cho phép người dùng thay đổi kích thước thủ công (`config.setResizable(true)`) và hiển thị đầy đủ viền trang trí (`config.setDecorated(true)`).
* **Kết quả:** Một cửa sổ game gọn gàng, sắc nét hiện ra ngay chính giữa màn hình thực tế với đầy đủ thanh công cụ điều khiển tiêu chuẩn. Hệ thống `FitViewport` bên trong vẫn giữ nguyên cấu trúc đồ họa Full HD sắc nét mà không hề bị mờ hay méo ảnh.

### 5.3. Lỗi trùng tên biến Preferences và Bug logic bộ đếm số lần vỗ cánh (Flaps)
* **Vấn đề:** Trong hàm `update()` của `PlayScreen.java`, việc khai báo lại biến trùng tên `Preferences prefs` ở khối lệnh kiểm tra chim rơi chạm đất nằm lồng bên trong phương thức gây ra lỗi biên dịch nghiêm trọng (Variable Name Collision). Đồng thời, do lỗi đóng dấu ngoặc nhọn `}` sai vị trí của khối điều kiện bấm phím, đoạn code cộng dồn `totalFlaps` bị đẩy ra ngoài vòng lặp chính, dẫn đến việc bộ đếm tự động tăng liên tục 60 lần/giây bất kể người chơi có bấm nút nhảy hay không.
* **Hành động giải quyết:** Tiến hành đổi tên biến cục bộ ở khối lệnh Game Over phía dưới thành `gamePrefs` để cô lập phạm vi khai báo biến. Rà soát cấu trúc dấu ngoặc và đưa toàn bộ đoạn code đọc/ghi số lần vỗ cánh vào nằm chính xác bên trong khối điều kiện `if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE))`.
* **Kết quả:** Lỗi báo đỏ biên dịch biến mất hoàn toàn. Bộ đếm số lần vỗ cánh vận hành vô cùng chuẩn xác, chỉ tăng lên đúng 1 đơn vị khi người chơi thực hiện thao tác click chuột hoặc đập phím Space.

### 5.4. Chữ hiển thị bị vỡ hạt răng cưa và Nhạc nền bị ngắt quãng đột ngột khi mở Pause
* **Vấn đề:** Do font chữ mặc định hệ thống bị phóng đại quy mô (Scale) lên 5-6 lần khiến các đường nét bị vỡ hạt pixel nặng. Đồng thời, lệnh ngắt nhạc `AssetLoader.bgm.pause()` nằm sót lại trong khối xử lý phím ESC khiến nhạc nền bị dừng đột ngột bất cứ khi nào người chơi tạm dừng ván đấu.
* **Hành động giải quyết:** Bật bộ lọc khử răng cưa `TextureFilter.Linear` trực tiếp cho font chữ hệ thống tại hàm khởi tạo Screen. Tiến hành loại bỏ hoàn toàn dòng lệnh ngắt nhạc nền `.pause()` ra khỏi khối xử lý phím bấm tạm dừng.
* **Kết quả:** Toàn bộ hệ thống chữ hiển thị trở nên mềm mại, mịn màng tuyệt đối. Giai điệu nhạc nền ngân vang du dương liên tục xuyên suốt từ màn hình chơi vào màn hình Pause và giữ mạch cảm xúc liền mạch cho người chơi.

---

## 6. Kết luận

### 6.1. Kết quả đạt được
Sau giai đoạn thực nghiệm và tinh chỉnh UI/UX bài bản, dự án đã đạt được các cột mốc kỹ thuật vững chắc:
* **Xây dựng thành công bộ khung Engine đồ họa 2D chuẩn mực:** Thiết lập vòng lặp game ổn định tuyệt đối tại mốc 60 FPS trên môi trường máy tính Desktop.
* **Đồng bộ hóa hoàn mỹ thiết kế UI/UX hình học:** Chuẩn hóa toàn bộ cấu trúc tấm nền Panel trung tâm và nút bấm điều hướng, loại bỏ hoàn toàn hiện tượng giật lắc thị giác khi chuyển đổi qua lại giữa các phân cảnh.
* **Hoàn thiện hạ tầng quản lý dữ liệu an toàn:** Tích hợp bộ lưu trữ Preferences cục bộ giúp game tự động thu thập và ghi nhớ toàn bộ thành tựu của người dùng vĩnh viễn trên ổ cứng máy tính.
* **Làm chủ kỹ thuật đồ họa và âm thanh nâng cao:** Ứng dụng thành công các bộ lọc đồ họa Linear khử răng cưa chữ, hiệu ứng mờ nền Dim Overlay thời gian thực và live mixing âm lượng đa luồng chuẩn xác.

### 6.2. Hướng phát triển tiếp theo
* **Tích hợp hiệu ứng xoay vật lý cho chú chim (Rotation Physics):** Can thiệp sâu vào tham số góc xoay trong lệnh `batch.draw()`, cho phép đầu chú chim ngóc lên $+20^\circ$ khi bấm phím vỗ cánh nhảy và tự động chúi thẳng đứng $-90^\circ$ xuống đất theo gia tốc rơi tự do thực tế.
* **Tích hợp font chữ tùy chỉnh bên ngoài (Custom TTF Font):** Ứng dụng thư viện mở rộng `FreeTypeFontGenerator` để nạp trực tiếp các file font pixel 8-bit hoặc arcade cổ điển từ bên ngoài, giúp định hình phong cách mỹ thuật độc quyền chuyên nghiệp cho tựa game.
* **Xây dựng hệ thống cửa hàng trang phục (Skin Shop):** Khai thác chỉ số `TOTAL POINTS ACQUIRED` (Tổng điểm tích lũy trong bảng thành tựu) để biến nó thành đơn vị tiền tệ trong game. Phát triển màn hình Shop cho phép người chơi dùng điểm tích lũy để mua và mở khóa các kết cấu chú chim có màu sắc mới hoặc hiệu ứng vệt đuôi (Particle Trail) lộng lẫy khi bay.

---

> Dự án được thiết kế, lập trình và hoàn thiện trọn vẹn bởi sinh viên thuộc **Học viện Công nghệ Bưu chính Viễn thông (PTIT)**.