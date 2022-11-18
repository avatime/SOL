// @ts-check
// @ts-ignore
import bezierEasing from "https://cdn.skypack.dev/bezier-easing@2.1.0";

const ease = bezierEasing(0.25, 0.1, 0.25, 1.0);
const easeIn = bezierEasing(0.38, 0.01, 0.78, 0.13);
const midSlow = bezierEasing(0, 0.7, 1, 0.3);

const def = new Map([
  [
    "slide1",
    {
      id: "slide1",
      top: 500,
      bottom: 1900,
      topStyle: {
        opacity: 0,
        translateY: -60,
      },
      bottomStyle: {
        opacity: 0,
        translateY: 60,
      },
      animations: [
        {
          enabled: false,
          top: 500,
          bottom: 1900,
          easing: midSlow,
          styles: [
            {
              name: "translateY",
              topValue: 60,
              bottomValue: -60,
            },
          ],
        },
        {
          enabled: false,
          top: 500,
          bottom: 800,
          easing: ease,
          styles: [
            {
              name: "opacity",
              topValue: 0,
              bottomValue: 1,
            },
          ],
        },
        {
          enabled: false,
          top: 1400,
          bottom: 1900,
          easing: easeIn,
          styles: [
            {
              name: "opacity",
              topValue: 1,
              bottomValue: 0,
            },
          ],
        },
      ],
    },
  ],
  [
    "scroll-down",
    {
      id: "scroll-down",
      top: 0,
      bottom: 1000,
      topStyle: {
        opacity: 1,
      },
      bottomStyle: {
        opacity: 0,
      },
      animations: [
        {
          enabled: false,
          top: 600,
          bottom: 1000,
          easing: easeIn,
          styles: [
            {
              name: "opacity",
              topValue: 1,
              bottomValue: 0,
            },
          ],
        },
      ],
    },
  ],
  [
    "slide2",
    {
      id: "slide2",
      top: 1900,
      bottom: 3200,
      topStyle: {
        opacity: 0,
        translateY: -60,
      },
      bottomStyle: {
        opacity: 0,
        translateY: 60,
      },
      animations: [
        {
          enabled: false,
          top: 1900,
          bottom: 3200,
          easing: midSlow,
          styles: [
            {
              name: "translateY",
              topValue: 60,
              bottomValue: -60,
            },
          ],
        },
        {
          enabled: false,
          top: 1900,
          bottom: 2500,
          easing: ease,
          styles: [
            {
              name: "opacity",
              topValue: 0,
              bottomValue: 1,
            },
          ],
        },
        {
          enabled: false,
          top: 2600,
          bottom: 3200,
          easing: easeIn,
          styles: [
            {
              name: "opacity",
              topValue: 1,
              bottomValue: 0,
            },
          ],
        },
      ],
    },
  ],
  [
    "slide3",
    {
      id: "slide3",
      top: 3300,
      bottom: 4600,
      topStyle: {
        opacity: 0,
      },
      bottomStyle: {
        opacity: 0,
      },
      animations: [
        {
          enabled: false,
          top: 3300,
          bottom: 4600,
          easing: midSlow,
          styles: [
            {
              name: "translateY",
              topValue: 60,
              bottomValue: -60,
            },
          ],
        },
        {
          enabled: false,
          top: 3300,
          bottom: 3900,
          easing: ease,
          styles: [
            {
              name: "opacity",
              topValue: 0,
              bottomValue: 1,
            },
          ],
        },
        {
          enabled: false,
          top: 4000,
          bottom: 4600,
          easing: easeIn,
          styles: [
            {
              name: "opacity",
              topValue: 1,
              bottomValue: 0,
            },
          ],
        },
      ],
    },
  ],
  [
    "moving-background",
    {
      id: "moving-background",
      top: 4500,
      bottom: 5900,
      topStyle: {
        opacity: 0,
        translateY: 300,
      },
      bottomStyle: {
        opacity: 0,
        translateY: 0,
      },
      animations: [
        {
          enabled: false,
          top: 4500,
          bottom: 5300,
          easing: ease,
          styles: [
            {
              name: "opacity",
              topValue: 0,
              bottomValue: 1,
            },
            {
              name: "translateY",
              topValue: 200,
              bottomValue: 0,
            },
          ],
        },
        {
          enabled: false,
          top: 5300,
          bottom: 5900,
          easing: easeIn,
          styles: [
            {
              name: "opacity",
              topValue: 1,
              bottomValue: 0,
            },
          ],
        },
      ],
    },
  ],
  [
    "slide4",
    {
      id: "slide4",
      top: 4700,
      bottom: 6000,
      topStyle: {
        opacity: 0,
      },
      bottomStyle: {
        opacity: 0,
      },
      animations: [
        {
          enabled: false,
          top: 4700,
          bottom: 6000,
          easing: midSlow,
          styles: [
            {
              name: "translateY",
              topValue: 60,
              bottomValue: -60,
            },
          ],
        },
        {
          enabled: false,
          top: 4700,
          bottom: 5300,
          easing: ease,
          styles: [
            {
              name: "opacity",
              topValue: 0,
              bottomValue: 1,
            },
          ],
        },
        {
          enabled: false,
          top: 5400,
          bottom: 6000,
          easing: easeIn,
          styles: [
            {
              name: "opacity",
              topValue: 1,
              bottomValue: 0,
            },
          ],
        },
      ],
    },
  ],
  [
    "slide5",
    {
      id: "slide5",
      top: 6100,
      bottom: 9000,
      topStyle: {
        opacity: 0,
      },
      bottomStyle: {
        opacity: 0,
      },
      animations: [
        {
          enabled: false,
          top: 6100,
          bottom: 7100,
          easing: midSlow,
          styles: [
            {
              name: "translateY",
              topValue: 60,
              bottomValue: -60,
            },
          ],
        },
        {
          enabled: false,
          top: 6100,
          bottom: 6700,
          easing: ease,
          styles: [
            {
              name: "opacity",
              topValue: 0,
              bottomValue: 1,
            },
          ],
        },
      ],
    },
  ],
]);

const enabled = new Map();
const disabled = new Map();

/**
 * 해당 숫자가 `top`과 `bottom` 사이에 있는지 확인하는 함수
 * @param {number} num
 * @param {number} top
 * @param {number} bottom
 */
function isAmong(num, top, bottom) {
  return num >= top && num <= bottom;
}

/**
 * top과 bottom 사이에서 비율을 통해 특정 값을 뽑아내는 함수
 * @param {number} top
 * @param {number} bottom
 * @param {number} rate 0~1
 */
function getPoint(top, bottom, rate) {
  return top + (bottom - top) * rate;
}

/**
 * DOM 요소에 스타일을 적용하는 함수
 * @param {HTMLElement} element
 * @param {string} styleName
 * @param {number} value
 */
function applyStyle(element, styleName, value) {
  if (styleName === "translateY") {
    element.style.transform = `translateY(${value}px)`;
    return;
  }
  if (styleName === "translateX") {
    element.style.transform = `translateX(${value}px)`;
    return;
  }
  // @ts-ignore
  element.style[styleName] = value;
}

/** @type {{[key: string]: HTMLElement}} */
const elements = {
  // @ts-ignore
  "sticky-container": document.getElementById("sticky-container"),
  // @ts-ignore
  "scroll-down": document.getElementById("scroll-down"),
  // @ts-ignore
  slide1: document.getElementById("slide1"),
  // @ts-ignore
  slide2: document.getElementById("slide2"),
  // @ts-ignore
  slide3: document.getElementById("slide3"),
  // @ts-ignore
  "moving-background": document.getElementById("moving-background"),
  // @ts-ignore
  slide4: document.getElementById("slide4"),
  // @ts-ignore
  slide5: document.getElementById("slide5"),
};

function onScroll() {
  // 현재 스크롤 위치 파악
  const scrollTop = window.scrollY || window.pageYOffset;
  const currentPos = scrollTop + window.innerHeight / 2;

  // disabled 순회하며 활성화할 요소 찾기.
  disabled.forEach((obj, id) => {
    // 만약 칸에 있다면 해당 요소 활성화
    if (isAmong(currentPos, obj.top, obj.bottom)) {
      enabled.set(id, obj);
      elements[id].classList.remove("disabled");
      elements[id].classList.add("enabled");
      disabled.delete(id);
    }
  });

  // enabled 순회하면서 헤제할 요소를 체크
  enabled.forEach((obj, id) => {
    const { top, bottom, topStyle, bottomStyle } = obj;

    // 범위 밖에 있다면
    if (!isAmong(currentPos, top, bottom)) {
      // 위로 나갔다면 시작하는 스타일 적용
      if (currentPos <= top) {
        Object.keys(topStyle).forEach((styleName) => {
          applyStyle(elements[id], styleName, topStyle[styleName]);
        });
      }
      // 아래로 나갔다면 끝나는 스타일적용
      else if (currentPos >= bottom) {
        Object.keys(bottomStyle).forEach((styleName) => {
          applyStyle(elements[id], styleName, bottomStyle[styleName]);
        });
      }

      // 리스트에서 삭제하고 disabled로 옮김.
      disabled.set(id, obj);
      elements[id].classList.remove("enabled");
      elements[id].classList.add("disabled");
      enabled.delete(id);
    }

    // enable 순회중, 범위 내부에 제대로 있다면 각 애니메이션 적용시키기.
    else {
      applyAnimations(currentPos, id);
    }
  });
}

window.addEventListener("scroll", onScroll);

function initAnimation() {
  // Sticky Conainer 의 높이를 설정함.
  elements["sticky-container"].style.height = `7100px`;

  // 모든 요소를 disabled 에 넣음.
  def.forEach((obj, id) => {
    disabled.set(id, obj);
  });

  // 초기 스타일 적용
  disabled.forEach((obj, id) => {
    Object.keys(obj.topStyle).forEach((styleName) => {
      const pushValue = obj.topStyle[styleName];
      applyStyle(elements[id], styleName, pushValue);
    });
  });

  // 이미 요소의 범위 및 애니메이션의 범위에 있는 것들을 렌더링하기 위해
  // 임의로 스크롤 이벤트 핸들러를 한 번 실행시킴.
  onScroll();
}

initAnimation();

/**
 *
 * @param {string} id
 * @param {any[]} styles
 * @param {number} rate
 */
function applyStyles(id, styles, rate) {
  styles.forEach((style) => {
    const { name, topValue, bottomValue } = style;
    const value = getPoint(topValue, bottomValue, rate);
    applyStyle(elements[id], name, value);
  });
}

/**
 *
 * @param {number} currentPos
 * @param {string} id
 */
function applyAnimations(currentPos, id) {
  const animations = def.get(id)?.animations;
  if (!animations) {
    return;
  }

  animations.forEach((animation) => {
    const { top: a_top, bottom: a_bottom, easing, styles } = animation;
    const isIn = isAmong(currentPos, a_top, a_bottom);
    // 만약 애니메이션이 새롭게 들어갈 때 혹은 나갈때 enabled 설정
    if (isIn && !animation.enabled) {
      animation.enabled = true;
    } else if (!isIn && animation.enabled) {
      if (currentPos <= a_top) {
        applyStyles(id, styles, 0);
      } else if (currentPos >= a_bottom) {
        applyStyles(id, styles, 1);
      }
      animation.enabled = false;
    }

    // 애니메이션이 enabled 라면, 애니메이션 적용.
    if (animation.enabled) {
      const rate = easing((currentPos - a_top) / (a_bottom - a_top));
      applyStyles(id, styles, rate);
    }
  });
}
