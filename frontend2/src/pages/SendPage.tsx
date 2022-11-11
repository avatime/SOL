import {
  Autocomplete,
  Box,
  Button,
  MenuItem,
  Select,
  Stack,
  TextField,
  Typography,
} from "@mui/material";
import { TransitionProps } from "@mui/material/transitions";
import React from "react";
import { useState } from "react";
import { useSearchParams } from "react-router-dom";
import BankInfoRes from "../apis/response/BankInfoRes";
import SelectBankBottomDialog from "../components/SelectBankBottomDialog";
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";

function SendPage() {
  const [searchParams] = useSearchParams();

  const senderName = "'보내는 사람 이름'";
  // api 쓸 때, 써야할 녀석들
  const acName = "";
  const acSend = "";
  const value = 1000000;
  //

  // decodeURIComponent(window.atob("JUVCJUE2JUFDJUVDJUEzJUJDJUVDJTk3JTg5JTJGJUVBJUI4JUIwJUVDJTk3JTg1JTJGMTg5NzM4NDA2NDU2OTElMkY1NQ=="))

  const [openBottomDialog, setOpenBottomDialog] = useState(false);
  const [bankInfo, setBankInfo] = useState<BankInfoRes | null>(null);
  const [acReceive, setAcReceive] = useState("");

  return (
    <Box p={2} display="flex" flexDirection="column" height="100vh">
      <Stack direction="row" alignItems="center">
        <img src="/favicon.ico" width="24px" height="24px" />
        <Typography mx={1} variant="subtitle2" fontWeight="bold">
          SOL#
        </Typography>
      </Stack>
      <Typography mt={2} variant="body2" color="primary">
        {senderName}님이
        <br />
        {value.toLocaleString()}원을 보냈어요.
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
          <p style={{ fontSize: "12px" }}>{bankInfo?.cp_name ?? "은행"}</p>
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

      <Box flex="1" />
      <Button variant="contained" size="large" onClick={() => setOpenBottomDialog(true)}>
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
