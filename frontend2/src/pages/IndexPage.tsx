import { useEffect } from "react";

export default function IndexPage() {
  useEffect(() => {
    const script = document.createElement("script");
    script.src = "/script.js";
    script.async = true;
    script.type = "module" 
    document.body.appendChild(script);
  }, []);
  return (
    <main className="sticky-container" id="sticky-container">
      <div className="sticky">
        <div className="slide-container">
          <div className="slide" id="slide1">
            <div className="slide-big-text">
              <p>안녕하세요.</p>
            </div>
          </div>
          <div id="scroll-down">
            <div className="scroll-down-text">아래로 스크롤하세요.</div>
          </div>
          <div className="slide" id="slide2">
            <div className="slide-big-text">
              <p>처음 뵙겠습니다.</p>
            </div>
          </div>
          <div className="slide" id="slide3">
            <div className="slide-big-text">
              <p>신한은행 기업 연계 프로젝트</p>
              <p>통합형 금융 플랫폼 SOL# 입니다.</p>
            </div>
          </div>
          <div className="slide slide-left" id="slide4">
            <div className="slide4-content">
              <div className="slide-big-text">
                <p>송금을 비롯한 은행 업무 및</p>
                <p>보험, 카드, 투자까지</p>
                <p>모두 한번에</p>
              </div>
              <div className="slide-small-text">
                <p>출석체크와 건강 관리까지!</p>
              </div>
            </div>
          </div>
          <div id="moving-background">나중에 넣은 멋진 배경</div>

          <div className="slide slide-left" id="slide5">
            <div className="slide-big-text">
              <p>SOL#은</p>
              <p>당신의 간편한</p>
              <p>금융활동을 꿈꿉니다.</p>
            </div>
            <div className="go-surf-wrapper">
              <a
                href="https://media.istockphoto.com/vectors/men-working-road-sign-vector-id856987802?k=20&m=856987802&s=612x612&w=0&h=jh0oh57xCzZ5UI9wKWNHFE_gRsl-HuZSO8rvhs4hjjo="
                target="_blank"
              >
                지금 바로 다운로드 하세요.
              </a>
            </div>
          </div>
        </div>
      </div>
    </main>
  );
}
