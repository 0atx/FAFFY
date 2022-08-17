/* eslint-disable no-extra-boolean-cast */
const rules = {
  require() {
    return v => !!v || '필수 입력 항목 입니다.';
  },
  requirePassword() {
    return v => !!v || '8~16자 영문 대 소문자, 숫자, 특수문자를 사용하여 입력하세요.';
  },
  requireNickname() {
    return v => !!v || '2~10자 특수문자를 제외한 별명을 입력하세요.';
  },
  consultMin() {
    return v => !!v ? v >= 2 || `최대 참여 인원 설정은 최소 2명부터 가능합니다.`: true;
  },
  consultMax() {
    return v => !!v ? v <= 50 || `최대 참여 인원 설정은 최대 50명까지 가능합니다.`: true;
  },
  min({len=8}) {
    return v => !!v ? v.length >= len || `${len}자 이상 입력하세요.`: true;
  },
  maxlen({maxlen=16}) {
    return v => !!v ? v.length <= maxlen || `${maxlen}자 이하로 입력하세요.`: true;
  },
  pattern({label, pattern}) {
    return v => !!v ? pattern.test(v) || `${label} 형식에 맞게 입력하세요.` : true;
  },
  patternNum({pattern}) {
    return v => !!v ? pattern.test(v) || `숫자만 입력 가능합니다.` : true;
  },
  url({label, url}) {
    return v => !!v ? v.indexOf(url) > -1 || `${label} 형식에 맞게 입력하세요.` : true;
  },
  matchValue(origin) {
    return v => origin === v || '비밀번호가 일치하지 않습니다.';
  },
  maxRoom(options) {
    const defaultOptions = {
      label: '최대 인원',
      required: true,
      pattern: /^[0-9]+$/,
    };
    const opt = Object.assign(defaultOptions, options);
    const arr = [];
    if(opt.required) {
      arr.push(rules.require(opt));
    }
    arr.push(rules.consultMin(opt));
    arr.push(rules.consultMax(opt));
    arr.push(rules.patternNum(opt));
    return arr;
  },
  consultingTitle(options) {
    const defaultOptions = {
      label: '제목',
      required: true,
      maxlen: 50,
    };
    const opt = Object.assign(defaultOptions, options);
    const arr = [];
    if(opt.required) {
      arr.push(rules.require(opt));
    }
    arr.push(rules.maxlen(opt));
    return arr;
  },
  consultingIntro(options) {
    const defaultOptions = {
      label: '방송 소개',
      required: true,
      maxlen: 300,
    };
    const opt = Object.assign(defaultOptions, options);
    const arr = [];
    if(opt.required) {
      arr.push(rules.require(opt));
    }
    arr.push(rules.maxlen(opt));
    return arr;
  },
  email(options) {
    const defaultOptions = {
      label: '이메일',
      required: true,
      maxlen: 320,
      pattern: /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/,
    };
    const opt = Object.assign(defaultOptions, options);
    const arr = [];
    if(opt.required) {
      arr.push(rules.require(opt));
    }
    arr.push(rules.maxlen(opt));
    arr.push(rules.pattern(opt));
    return arr;
  },
  password(options) {
    const defaultOptions = {
      label: '비밀번호',
      required: true,
      len: 8,
      maxlen: 16,
      pattern: /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,16}$/,
    };
    const opt = Object.assign(defaultOptions, options);
    const arr = [];
    if(opt.required) {
      arr.push(rules.requirePassword(opt));
    }
    arr.push(rules.min(opt));
    arr.push(rules.maxlen(opt));
		arr.push(rules.pattern(opt));
    return arr;
  },
  name(options) {
    const defaultOptions = {
      label: '이름',
      required: true,
      len:2,
      maxlen: 30,
      pattern: /^[가-힣]+$/,
    };
    const opt = Object.assign(defaultOptions, options);
    const arr = [];
    if(opt.required) {
      arr.push(rules.require(opt));
    }
    arr.push(rules.min(opt));
    arr.push(rules.maxlen(opt));
    arr.push(rules.pattern(opt));
    return arr;
  },
  nickname(options) {
    const defaultOptions = {
      label: '별명',
      required: true,
      len:2,
      maxlen: 10,
      pattern: /^([가-힣a-zA-Z0-9])+$/,
    };
    const opt = Object.assign(defaultOptions, options);
    const arr = [];
    if(opt.required) {
      arr.push(rules.requireNickname(opt));
    }
    arr.push(rules.min(opt));
    arr.push(rules.maxlen(opt));
    arr.push(rules.pattern(opt));
    return arr;
  },
  birth(options) {
    const defaultOptions = {
      label: '생년월일',
      required: true,
    };
    const opt = Object.assign(defaultOptions, options);
    const arr = [];
    if(opt.required) {
      arr.push(rules.require(opt));
    }
    return arr;
  },
  gender(options) {
    const defaultOptions = {
      label: '성별',
      required: true,
    };
    const opt = Object.assign(defaultOptions, options);
    const arr = [];
    if(opt.required) {
      arr.push(rules.require(opt));
    }
    return arr;
  },
  instagram(options) {
    const defaultOptions = {
      label: '인스타그램 링크',
      url: "https://www.instagram.com/",
    };
    const opt = Object.assign(defaultOptions, options);
    const arr = [];
    arr.push(rules.url(opt));
    return arr;
  },
  facebook(options) {
    const defaultOptions = {
      label: '페이스북 링크',
      url: "https://www.facebook.com/",
    };
    const opt = Object.assign(defaultOptions, options);
    const arr = [];
    arr.push(rules.url(opt));
    return arr;
  },
  youtube(options) {
    const defaultOptions = {
      label: '유튜브 링크',
      url: "https://www.youtube.com/c/",
    };
    const opt = Object.assign(defaultOptions, options);
    const arr = [];
    arr.push(rules.url(opt));
    return arr;
  }
};

module.exports = rules;
