import { Box, Button, Stack, Typography } from "@mui/material";
import { useSearchParams } from "react-router-dom";

function SendPage() {
  const [searchParams] = useSearchParams();

  const senderName = "'보내는 사람 이름'"
  // api 쓸 때, 써야할 녀석들
  const acName = "";
  const acTag = "";
  const acSend = "";
  const acReceive = "";
  const value = 1000000;
  //

  return (
    <Box p={2} display="flex" flexDirection="column" height="100vh">
      <Stack direction="row" alignItems="center">
        <img src="/favicon.ico" width="24px" height="24px" />
        <Typography mx={1} variant="h6" component="h6" fontWeight="bold">
          SOL#
        </Typography>
      </Stack>
      <Typography mt={2} variant="subtitle1" component="h6" color="primary">
        {senderName}님이<br/>{value.toLocaleString()}원을 보냈어요.
      </Typography>
      <Typography mt={2} variant="h4" component="h4" fontWeight="bold">
        어디로 받을까요?
      </Typography>
      <Box flex="1"/>
      <Button variant="contained" size="large">
        입금 받기
      </Button>
    </Box>
  );
}

export default SendPage;
