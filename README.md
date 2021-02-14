# HotHotItemPlugin

## 概要

アイテム熱アツクラフト

- アイテムが熱すぎて3秒しか持てない
- 3秒持つとダメージを喰らい出す

## 動作条件

- Paper 1.15.2

## プレイの流れ

1. 適当にパラメータを設定する（詳細は以下を参照）
1. `/hotitem start` でゲームスタート！
1. `/hotitem stop` でゲーム終了！

## 詳細

- アイテムは `time` 秒だけ持つことができ、これ以上持ち続けると発火する。
- 発火すると、少なくとも `damage` だけダメージが入る。
- これに加え、` (持ってるアイテム数 - 1) * coefficient `だけ追加ダメージが加わる。

  <br/>
  
  すなわち、最終的にプレイヤーに入るダメージは次の式で表される
  
  <br/>
<div align="center">
  <img src="https://latex.codecogs.com/png.latex?given\_damage=coefficient\times(count-1)&plus;damage" />
</div>
  <br/>

　　ただしプレイヤーが持ってるアイテム数を `count` とする。
  
  <br/>

- `time`, `damage`, `coefficient`, `period` はコマンドとconfig.ymlから変更できる。

### コマンド一覧

| cmd | arg[0] | arg[1] | description | default value |
| :---: | :---: | :---: | :---: | :---: | 
| hotitem | help | | ヘルプ |  |
|  | start |  | ゲーム開始 | |
|  | stop |  | ゲーム終了 | |
|  | time | < double > | 耐えられる時間 (sec) | 3.0 |
|  | damage | < double > | 与ダメージの基準値 | 1.0 |
|  | coefficient | < double > | アイテムが一個増えたときのダメージの増加率 | 1.0 |
|  | period | < int > | インベントリチェックのインターバル (ticks) | 20 |
|  | loadconfig |  | コンフィグの読み出し（リロード） |  |


### 高度な設定

- 内部では BukkitRunnable の runTaskTimer により繰り返し処理をしている。
- 繰り返し処理の頻度は `period` により設定できる。
- 重い場合にはこの値を大きくすればよい。
- `period` を20未満にした場合、ダメージが入らなくなるので注意が必要。