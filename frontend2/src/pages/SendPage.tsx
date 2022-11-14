import { Box, Button, Stack, TextField, Typography } from "@mui/material";
import React, { useEffect, useMemo } from "react";
import { useState } from "react";
import { useSearchParams } from "react-router-dom";
import BankInfoRes from "../apis/response/BankInfoRes";
import SelectBankBottomDialog from "../components/SelectBankBottomDialog";
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";
import ExpirationTokenPage from "./ExpirationTokenPage";
import ApiClient from "../apis/ApiClient";
import SuccessPage from "./SuccessPage";

function SendPage() {
  const [searchParams] = useSearchParams();

  const data = useMemo(() => {
    try {
      const query = decodeURIComponent(window.atob(searchParams.get("query")!!.toString()));
      const splited = query.split("/");
      return {
        senderName: splited[0],
        accountName: splited[1],
        accountNumber: splited[2],
        money: splited[3],
        token: splited[4],
      };
    } catch {
      return null;
    }
  }, [searchParams]);

  useEffect(() => {
    if (data == null) {
      return;
    }

    ApiClient.getInstance()
      .checkToken(+data.token)
      .then((value) => {
        if (!value) {
          setShowExpiration(true);
        }
      })
      .catch(() => setShowExpiration(true));
  }, [data]);

  const [openBottomDialog, setOpenBottomDialog] = useState(false);
  const [bankInfo, setBankInfo] = useState<BankInfoRes | null>(null);
  const [acReceive, setAcReceive] = useState("");
  const [showSuccess, setShowSuccess] = useState(false);
  const [showExpiration, setShowExpiration] = useState(false);

  const receive = () => {
    ApiClient.getInstance()
      .remit(
        data!!["accountName"],
        data!!["accountNumber"],
        bankInfo!!.cp_name,
        acReceive,
        +data!!["money"],
        +data!!["token"]
      )
      .then(() => setShowSuccess(true))
      .catch(() => alert("알 수 없는 오류가 발생했습니다. 잠시 후 다시 시도해주세요."));
  };

  if (!data || showExpiration) {
    return <ExpirationTokenPage />;
  } else if (showSuccess) {
    return <SuccessPage money={+data["money"]} />;
  }
  return (
    <Box p={2} display="flex" flexDirection="column" height="100vh">
      <Stack direction="row" alignItems="center">
        <img src="/favicon.ico" width="24px" height="24px" />
        <Typography mx={1} variant="subtitle2" fontWeight="bold">
          SOL#
        </Typography>
      </Stack>
      <Typography mt={2} variant="body2" color="primary">
        {data["senderName"]}님이
        <br />
        {data["money"]!!.toLocaleString()}원을 보냈어요.
      </Typography>
      <Typography mt={2} variant="h6" fontWeight="bold">
        어디로 받을까요?
      </Typography>
      <Box m={1} />
      <Button
        variant="outlined"
        onClick={() => setOpenBottomDialog(true)}
        sx={{ borderRadius: "20px" }}
        size="large"
        color="secondary"
      >
        <Box display="flex" flexDirection="row" alignItems="center" width="100%">
          {bankInfo && (
            <img
              src={bankInfo.cp_logo}
              style={{ width: "24px", height: "24px", borderRadius: "20px" }}
            />
          )}
          <Box m={1} />
          <span style={{ fontSize: "12px" }}>{bankInfo?.cp_name ?? "은행"}</span>
          <Box flex="1" />
          <ArrowDropDownIcon />
        </Box>
      </Button>
      <Box m={2} />
      <TextField
        label="계좌번호"
        variant="standard"
        color="secondary"
        value={acReceive}
        onChange={(it) => setAcReceive(it.target.value)}
        type="number"
      />
      <Box m={4} />
      <Button
        variant="contained"
        size="large"
        disabled={bankInfo == null || !acReceive}
        onClick={receive}
      >
        입금 받기
      </Button>
      <SelectBankBottomDialog
        open={openBottomDialog}
        onClose={() => setOpenBottomDialog(false)}
        onClickBankItem={(it) => {
          setOpenBottomDialog(false);
          setBankInfo(it);
        }}
      />
    </Box>
  );
}

export default SendPage;
