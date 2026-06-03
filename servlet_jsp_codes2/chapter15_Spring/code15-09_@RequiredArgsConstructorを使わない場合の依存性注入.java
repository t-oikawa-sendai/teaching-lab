// コード15-9 @RequiredArgsConstructorを使わない場合の依存性注入
@Controller
public class AddController {
// サービスが注入されるフィールド
private final AddService addService;
// 受け取ったサービスをフィールドに代入するコンストラクタ
public AddController(AddService addService) {
this.addService = addService;
}
…
}
難しかったら、まずはやり方だけでも覚えて、“こう書けば自
動で代入されるんだ ”って割り切ってしまおう。