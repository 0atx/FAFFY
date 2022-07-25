/* eslint-disable no-extra-boolean-cast */
const rules = {
  require() {
    return v => !!v || '필수 입력 항목 입니다.';
  },
  min({len=8}) {
    return v => !!v ? v.length >= len || `${len}자 이상 입력하세요.`: true;
  },
  maxlen({maxlen=16}) {
    return v => !!v ? v.length < maxlen || `${maxlen}자 미만으로 입력하세요.`: true;
  },
  pattern({label, pattern}) {
    return v => !!v ? pattern.test(v) || `${label} 형식에 맞게 입력하세요.` : true;
  },
  matchValue(origin) {
    return v => origin === v || '비밀번호가 일치하지 않습니다.';
  },
  email(options) {
    const defaultOptions = {
      label: '이메일',
      required: true,
      pattern: /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/,
    };
    const opt = Object.assign(defaultOptions, options);
    const arr = [];
    if(opt.required) {
      arr.push(rules.require(opt));
    }
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
      arr.push(rules.require(opt));
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
      pattern: /^[가-힣]+$/,
    };
    const opt = Object.assign(defaultOptions, options);
    const arr = [];
    if(opt.required) {
      arr.push(rules.require(opt));
    }
    arr.push(rules.min(opt));
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
      arr.push(rules.require(opt));
    }
    arr.push(rules.min(opt));
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
  }
};

module.exports = rules;