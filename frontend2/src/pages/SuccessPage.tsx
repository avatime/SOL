import { Box, Button, Stack, Typography } from "@mui/material";
import { useNavigate } from "react-router";

interface Props {
  money: Number;
}

export default function SuccessPage({ money }: Props) {
  const navigate = useNavigate();
  const onClickButton = () => {
    navigate("/");
  };
  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      height="100vh"
      p={2}
      pt={5}
    >
      <img src="/ic_done.gif" style={{ width: "100px", height: "100px" }} />
      <Typography variant="h6" my={2}>
        {money.toLocaleString()}원
      </Typography>
      <Typography variant="h6">송금 완료</Typography>
      <Box flex="1" />
      <Button fullWidth variant="contained" size="large" onClick={onClickButton}> 
        확인
      </Button>
    </Box>
  );
}
