import axios, { AxiosInstance } from "axios";
import BankInfoRes from "./response/BankInfoRes";
import CheckTokenRes from "./response/CheckTokenRes";

const BASE_URL = "https://k7a403.p.ssafy.io/";
const API_PATH = "api/v1";

interface BankApi {
  getBankInfos(): Promise<BankInfoRes[]>;
  getFinanceBankInfos(): Promise<BankInfoRes[]>;
  remit(
    senderAccountName: String,
    senderAccountNumber: String,
    receiverAccountName: String,
    receiverAccountNumber: String,
    money: Number,
    token: Number
  ): Promise<void>;
  checkToken(token: String): Promise<CheckTokenRes>;
}

export default class ApiClient implements BankApi {
  private static instance: ApiClient;
  private axiosInstance: AxiosInstance;

  constructor() {
    this.axiosInstance = this.createAxiosInstance();
  }

  async checkToken(token: String): Promise<CheckTokenRes> {
    return (
      await this.axiosInstance.request({
        method: "GET",
        url: `${API_PATH}/remit/phone/nonmember/${token}`,
      })
    ).data;
  }

  async remit(
    senderAccountName: String,
    senderAccountNumber: String,
    receiverAccountName: String,
    receiverAccountNumber: String,
    money: Number,
    token: Number
  ): Promise<void> {
    const data = {
      remit_info_req: {
        ac_name: senderAccountName,
        ac_tag: receiverAccountName,
        ac_send: senderAccountNumber,
        ac_receive: receiverAccountNumber,
        value: money,
        receive: "",
        send: "",
      },
      remit_available_res: {
        token_id: token,
        token: false,
      },
    };

    return (await this.axiosInstance.request({
      method: "POST",
      url: `${API_PATH}/remit/phone/nonmember`,
      data: data,
    }))
  }

  static getInstance(): ApiClient {
    return this.instance || (this.instance = new this());
  }

  async getBankInfos(): Promise<BankInfoRes[]> {
    return (
      await this.axiosInstance.request({
        method: "GET",
        url: `${API_PATH}/bank/info`,
      })
    ).data;
  }

  async getFinanceBankInfos(): Promise<BankInfoRes[]> {
    return (
      await this.axiosInstance.request({
        method: "GET",
        url: `${API_PATH}/bank/finance/info`,
      })
    ).data;
  }

  login(newToken: string) {
    this.axiosInstance = this.createAxiosInstance(newToken);
  }

  private createAxiosInstance = (token?: string) => {
    const headers: any = {
      "Content-Type": "application/json",
    };

    if (token) {
      headers["access_token"] = `Bearer ${token}`;
    }

    return axios.create({
      baseURL: BASE_URL,
      timeout: 1000,
      headers,
    });
  };
}
