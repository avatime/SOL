export default interface CheckTokenRes {
  token: boolean; //토큰 상태
  ac_name: string; // 보내는 계좌 은행
  ac_send: string; //보내는 계좌 번호
  value: Number; // 금액
  sender: string; // 보내는 사람 이름
}
