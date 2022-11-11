import axios, { AxiosInstance } from "axios";
import BankInfoRes from "./response/BankInfoRes";

const BASE_URL = "https://k7a403.p.ssafy.io/";
const API_PATH = "api/v1";

interface BankApi {
  getBankInfos(): Promise<BankInfoRes[]>;
  getFinanceBankInfos(): Promise<BankInfoRes[]>;
}

export default class ApiClient implements BankApi {
  private static instance: ApiClient;
  private axiosInstance: AxiosInstance;

  constructor() {
    this.axiosInstance = this.createAxiosInstance();
  }

  static getInstance(): ApiClient {
    return this.instance || (this.instance = new this());
  }

  async getBankInfos(): Promise<BankInfoRes[]> {
    return (await this.axiosInstance.request({
        method: "GET",
        url: `${API_PATH}/bank/info`
    })).data
  }

  async getFinanceBankInfos(): Promise<BankInfoRes[]> {
    return (await this.axiosInstance.request({
        method: "GET",
        url: `${API_PATH}/bank/finance/info`
    })).data
  }

  login(newToken: string) {
    this.axiosInstance = this.createAxiosInstance(newToken);
  }

  private createAxiosInstance = (token?: string) => {
    const headers: any = {
      "content-type": "application/json",
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
