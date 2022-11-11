import {
  Backdrop,
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Modal,
  Slide,
  Stack,
  Typography,
} from "@mui/material";
import { TransitionProps } from "@mui/material/transitions";
import React from "react";
import { useState } from "react";
import { useSearchParams } from "react-router-dom";
import BankInfoRes from "../apis/response/BankInfoRes";
import SelectBankBottomDialog from "../components/SelectBankBottomDialog";

function SendPage() {
  const [searchParams] = useSearchParams();

  const senderName = "'보내는 사람 이름'";
  // api 쓸 때, 써야할 녀석들
  const acName = "";
  const acTag = "";
  const acSend = "";
  const acReceive = "";
  const value = 1000000;
  //

  const [openBottomDialog, setOpenBottomDialog] = useState(false);
  const [bankInfo, setBankInfo] = useState<BankInfoRes>();

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
      <Box flex="1" />
      <Button variant="contained" size="large" onClick={() => setOpenBottomDialog(true)}>
        입금 받기
      </Button>
      <SelectBankBottomDialog
        open={openBottomDialog}
        onClose={() => setOpenBottomDialog(false)}
        onClickBankItem={(it) => {
          setBankInfo(it);
          setOpenBottomDialog(false);
        }}
      />
    </Box>
  );
}

export default SendPage;
